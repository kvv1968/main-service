package ru.platform.learning.mainservice.restcontroller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import ru.platform.learning.mainservice.entity.JavaFile;
import ru.platform.learning.mainservice.entity.TaskTemplate;
import ru.platform.learning.mainservice.exception.UserTaskException;
import ru.platform.learning.mainservice.model.LessonTopic;
import ru.platform.learning.mainservice.service.JavaFileService;
import ru.platform.learning.mainservice.service.TaskService;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@Slf4j
public class TaskRestController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private JavaFileService javaFileService;
    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/user/lesson/topics/task")
    public ResponseEntity<?> getAllTopics() {
        Map<String, String> mapTopics = LessonTopic.readTopicDescription();
        if (mapTopics.isEmpty()) {
            final String msg = "Error mapTopics isEmpty";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        return ResponseEntity.ok(mapTopics);
    }

    @GetMapping("/user/nav/{ordinal}")
    public ResponseEntity<?> getAllTasksByTopic(@PathVariable Integer ordinal) {
        if (ordinal == null) {
            final String mcg = "Ordinal is null";
            log.error(mcg);
            return ResponseEntity.status(400).body(mcg);
        }
        LessonTopic lessonTopic = LessonTopic.values()[ordinal];
        List<TaskTemplate> templates = taskService.findTaskTemplateByLessonTopic(lessonTopic);
        if (templates.isEmpty()) {
            final String mcg = "Error templates isEmpty";
            log.error(mcg);
            return ResponseEntity.status(400).body(mcg);
        }
        return ResponseEntity.ok(new HashMap<String, List<TaskTemplate>>() {{
            put(lessonTopic.getTopic(), templates);
        }});
    }

    @GetMapping("/user/nav/step/{id}")
    public ResponseEntity<?> getTaskTemplate(@PathVariable Long id) {
        if (id == null) {
            final String mcg = "Error Step is null";
            log.error(mcg);
            return ResponseEntity.status(400).body(mcg);
        }
        return ResponseEntity.ok(taskService.findTaskTemplateById(id));
    }

    @GetMapping("/user/ordinal/{topic}")
    public ResponseEntity<?> getTaskTemplate(@PathVariable String topic) {
        if (StringUtils.isEmpty(topic)) {
            final String mcg = "Error Topic is empty";
            log.error(mcg);
            return ResponseEntity.status(400).body(mcg);
        }
        Integer result = LessonTopic.valueOf(topic).ordinal();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/admin/api/tasks")
    public ResponseEntity<?> getAllTaskTemplates() {
        List<TaskTemplate> taskTemplates = taskService.getAllTaskTemplates();
        if (taskTemplates.isEmpty()) {
            final String msg = "List taskTemplates  is empty download file";
            log.error(msg);
            taskTemplates = Collections.emptyList();
        }
        return ResponseEntity.ok(taskTemplates);
    }


    @PostMapping("/api/task-is-question")
    public ResponseEntity<?> createTaskTemplate(@ModelAttribute TaskTemplate taskTemplate) {
        if (taskTemplate != null) {
            taskTemplate.setIsQuestion(true);
            TaskTemplate taskTemplateRepo = taskService.createTaskTemplate(taskTemplate);
            return ResponseEntity.ok(taskTemplateRepo);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/file")
    public ResponseEntity<?> getFileTask(@RequestParam MultipartFile file) throws IOException, UserTaskException {
        if (file == null) {
            final String msg = "Error file is null";
            log.error(msg);
            return ResponseEntity.status(400).body("Файл не загружен");
        }
        String fileName = file.getOriginalFilename();
        if (fileName.endsWith(".java")) {
            JavaFile javaFile = readJavaFile(file);
            try {
                JavaFile save = javaFileService.add(javaFile);
                if (save == null) {
                    final String msg = "Error javaFile is null";
                    log.error(msg);
                    return ResponseEntity.status(500).body(msg);
                }
            } catch (Exception ex) {
                log.error(ex.getMessage());
                return ResponseEntity.ok(ex.getMessage());
            }

            return ResponseEntity.ok("Файл JavaFile загрузился");
        }

        List<TaskTemplate> templates = readFile(file);
        try {
            taskService.createListTaskTemplates(templates);
            return ResponseEntity.ok("Файл загрузился");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.ok(ex.getMessage());
        }
    }


    @DeleteMapping("/api/task")
    public ResponseEntity<?> deleteTaskTemplate(@RequestParam String id) {
        Long idTask = Long.parseLong(id);
        taskService.deleteTaskTemplate(idTask);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/tasks/del")
    public ResponseEntity<?> deleteAll() {
        String msg;
        try {
            taskService.deleteAll();
            msg = "Все задания из БД удалились";
        } catch (Exception ex) {
            msg = ex.getMessage();
        }
        return ResponseEntity.ok(msg);
    }

    private List<TaskTemplate> readFile(MultipartFile file) throws IOException {
        if (file == null) {
            final String msg = "MultipartFile is null";
            log.error(msg);
            return Collections.emptyList();
        }
        InputStream is = file.getInputStream();
        List<TaskTemplate> templates = new ArrayList<>();
        ArrayNode arrayNode = mapper.readValue(is, ArrayNode.class);
        for (int i = 0; i < arrayNode.size(); i++) {
            JsonNode jsonNode = arrayNode.get(i);
            JsonParser jsonParser = jsonNode.traverse();
            TaskTemplate taskTemplate = mapper.readValue(jsonParser, TaskTemplate.class);
            templates.add(taskTemplate);
        }

        return templates;
    }

    private JavaFile readJavaFile(MultipartFile file) throws IOException, UserTaskException {
        InputStream is = file.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);

        JavaFile javaFile = new JavaFile();
        javaFile.setNameClass(Objects.requireNonNull(file.getOriginalFilename()).replace(".java", ""));
        javaFile.setBytes(bytes);
        TaskTemplate template = taskService.findTaskTemplateByNameClass(javaFile.getNameClass());
        if (template == null) {
            final String msg = "Error TaskTemplate is null";
            log.error(msg);
            throw new UserTaskException(msg);
        }
        javaFile.setTaskTemplate(template);
        return javaFile;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception ex) {
        final String msg = ex.getMessage();
        log.error(msg);
        return ResponseEntity.status(500).body(msg);
    }
}
