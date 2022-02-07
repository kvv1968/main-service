package ru.platform.learning.mainservice.restcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.platform.learning.mainservice.client.CompilerClient;
import ru.platform.learning.mainservice.dto.CompilerResult;
import ru.platform.learning.mainservice.dto.CompilerTask;
import ru.platform.learning.mainservice.entity.TaskTemplate;
import ru.platform.learning.mainservice.entity.User;
import ru.platform.learning.mainservice.entity.UserTask;
import ru.platform.learning.mainservice.model.ErrorValidation;
import ru.platform.learning.mainservice.model.LessonTopic;
import ru.platform.learning.mainservice.model.TaskData;
import ru.platform.learning.mainservice.service.TaskService;
import ru.platform.learning.mainservice.service.UserTaskService;
import ru.platform.learning.mainservice.validators.TaskDataValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserTaskRestController {

    private final TaskService taskService;
    private final UserTaskService userTaskService;
    private final CompilerClient compilerClient;
    private final TaskDataValidator taskDataValidator;

    @Autowired
    public UserTaskRestController(TaskService taskService,
                                  UserTaskService userTaskService,
                                  CompilerClient compilerClient,
                                  TaskDataValidator taskDataValidator) {
        this.taskService = taskService;
        this.userTaskService = userTaskService;
        this.compilerClient = compilerClient;
        this.taskDataValidator = taskDataValidator;
    }

    @PostMapping("/user-task")
    public ResponseEntity<?> createUserTask(@ModelAttribute TaskData taskData,
                                            HttpServletRequest request) {
        User user = getUserPrincipal(request);
        if(user == null){
            final String msg = "Error UserPrincipal is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        if(taskData == null){
            final String msg = "Error TaskData is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        taskData.setAnswer(handlersAnswer(taskData.getAnswer()));

        try {
            Set<ErrorValidation> validations = taskDataValidator.validate(taskData, user);
            if(!validations.isEmpty()){
                validations.forEach(error -> log.error(error.getMessage(), error.getNameField()));
                return ResponseEntity.status(500).body("Error create userTask not open");
            }

            TaskTemplate taskTemplate = taskService.findTaskTemplateById(taskData.getIdTask());
            if (taskTemplate == null){
                final String msg = "Error create userTask not open taskTemplate is null";
                log.error(msg);
                return ResponseEntity.status(500).body(msg);
            }
            CompilerTask compilerTask = createCompilerTask(
                    taskTemplate.getNameClass(),
                    taskData.getAnswer(),
                    taskTemplate.getJavaFile() != null ? taskTemplate.getJavaFile().getBytes() : null,
                    taskTemplate.getIsQuestion(),
                    taskTemplate.getCorrectAnswers());

            CompilerResult compilerResult = compilerClient.startCompiler(compilerTask);
            if (compilerResult == null){
                final String msg = "Error compilerResult is null";
                log.error(msg);
                return ResponseEntity.status(500).body(msg);
            }

            UserTask userTask = creationUserTask(
                    taskData.getAnswer(),
                    compilerResult.getMessage(),
                    compilerResult.getIsResultTask(),
                    taskTemplate,
                    user);

            UserTask userTaskRepo = userTaskService.add(userTask);
            if(userTaskRepo == null){
                final String msg = "Error UserTaskRepo is null";
                log.error(msg);
                return ResponseEntity.status(500).body(msg);
            }

            return ResponseEntity.ok(userTaskRepo);

        } catch (Exception ex){
            final String msg = ex.getMessage();
            log.error(msg, ex);
            return ResponseEntity.status(500).body(msg);
        }
    }

    @GetMapping("/user-tasks/topic/{ordinal}")
    public ResponseEntity<?> getUserTasksByUserAndTopic(@PathVariable Integer ordinal,
                                                        HttpServletRequest request){
        User user = getUserPrincipal(request);
        if(user == null){
            final String msg = "Error UserPrincipal is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        List<UserTask>taskList = userTaskService.getUserTasksByUserAndTopic(user.getId(), ordinal);
        if(taskList.isEmpty()){
            final String msg = "Error taskList isEmpty";
            log.error(msg);
            return ResponseEntity.status(500).body(msg);
        }
        Map<String, Boolean> mapResult = selectResult(taskList, ordinal);
        if(mapResult.isEmpty()){
            final String msg = "Error mapResult isEmpty";
            log.error(msg);
            return ResponseEntity.status(500).body(msg);
        }

        return ResponseEntity.ok(mapResult);
    }

    @GetMapping("/task-story/{step}")
    public ResponseEntity<?> getUserTasksStory(@PathVariable Long step,
                                               HttpServletRequest request){
        User user = getUserPrincipal(request);
        if(user == null){
            final String msg = "Error UserPrincipal is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        List<UserTask> results = userTaskService.findUserTasksStory(user.getId(), step);
        if(results.isEmpty()){
            final String msg = "Error results isEmpty";
            log.error(msg);
            return ResponseEntity.status(500).body(msg);
        }
        return ResponseEntity.ok(results);
    }

    private User getUserPrincipal(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        return (User) principal.getPrincipal();
    }

    private UserTask creationUserTask(String answer,
                                      String message,
                                      Boolean isResultTask,
                                      TaskTemplate taskTemplate,
                                      User user) {
        UserTask userTask = new UserTask();
        userTask.setAnswer(answer);
        userTask.setMessage(message);
        userTask.setIsResultTask(isResultTask);
        userTask.setTemplates(taskTemplate);
        userTask.setUser(user);
        return userTask;
    }

    private CompilerTask createCompilerTask(String nameClass,
                                            String answer,
                                            byte[] bytes,
                                            Boolean isQuestion,
                                            String correctAnswers) {
        CompilerTask compilerTask = new CompilerTask();
        compilerTask.setNameClass(nameClass);
        compilerTask.setAnswer(answer);
        compilerTask.setBytes(bytes);
        compilerTask.setIsQuestion(isQuestion);
        compilerTask.setCorrectAnswers(correctAnswers);
        return compilerTask;
    }


    private Map<String, Boolean> selectResult(List<UserTask> taskList, Integer ordinal) {
        List<TaskTemplate> templates = taskService.findTaskTemplateByLessonTopic(LessonTopic.values()[ordinal]);
        Map<String, Boolean> map = templates.stream()
                .collect(Collectors.toMap(TaskTemplate::getNameTask, value -> false));

        taskList.forEach(userTask -> {
            if(userTask.getIsResultTask()){
                map.replace(userTask.getTemplates().getNameTask(), true);
            }
        });

        return map;
    }

    private String handlersAnswer(String answer) {
        return answer.replace(",-", "#-");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception ex){
        final String msg = ex.getMessage();
        log.error(msg);
        return ResponseEntity.status(500).body(msg);
    }
}
