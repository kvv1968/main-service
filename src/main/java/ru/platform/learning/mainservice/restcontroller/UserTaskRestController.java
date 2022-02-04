package ru.platform.learning.mainservice.restcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.platform.learning.mainservice.client.UserTaskClient;
import ru.platform.learning.mainservice.entity.TaskTemplate;
import ru.platform.learning.mainservice.entity.User;
import ru.platform.learning.mainservice.entity.UserTask;
import ru.platform.learning.mainservice.model.LessonTopic;
import ru.platform.learning.mainservice.model.TaskData;
import ru.platform.learning.mainservice.service.TaskService;
import ru.platform.learning.mainservice.service.UserTaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserTaskRestController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserTaskService userTaskService;
    @Autowired
    private UserTaskClient userTaskClient;

    @PostMapping("/user-task")
    public ResponseEntity<?> createUserTask(@ModelAttribute TaskData taskData,
                                            HttpServletRequest request) {
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        User user = (User) principal.getPrincipal();
        if(user == null){
            final String msg = "Error UserPrincipal is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        taskData.setAnswer(handlersAnswer(taskData.getAnswer()));
        taskData.setUserId(user.getId());

        Long id = userTaskClient.startCompiler(taskData);
        if(id == null){
            final String msg = "Error UserTask id  is null";
            log.error(msg);
            return ResponseEntity.status(500).body(msg);
        }
        UserTask userTask = userTaskService.findById(id);
        if(userTask == null){
            final String msg = "Error UserTask is null";
            log.error(msg);
            return ResponseEntity.status(500).body(msg);
        }

        return ResponseEntity.ok(userTask);
    }


    @GetMapping("/user-tasks/topic/{ordinal}")
    public ResponseEntity<?> getUserTasksByUserAndTopic(@PathVariable Integer ordinal,
                                                        HttpServletRequest request){
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        User user = (User) principal.getPrincipal();
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
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        User user = (User) principal.getPrincipal();
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
