package ru.platform.learning.mainservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.learning.mainservice.entity.UserTask;
import ru.platform.learning.mainservice.exception.UserTaskException;
import ru.platform.learning.mainservice.model.LessonTopic;
import ru.platform.learning.mainservice.repository.UserTaskRepository;

import java.util.List;

@Service
@Slf4j
public class UserTaskService {

    @Autowired
    private UserTaskRepository userTaskRepository;


    public List<UserTask> getAllUserTasks() {
        return userTaskRepository.findAll();
    }


    public UserTask findById(Long id) {
        return userTaskRepository.findById(id).orElse(null);
    }


    public List<UserTask> getUserTasksByUserAndTopic(Long id, Integer ordinal) {
        LessonTopic topic = LessonTopic.values()[ordinal];
        return userTaskRepository.findUserTasksByUserAndLessonTopic(id, topic);
    }


    public List<UserTask> findUserTasksStory(Long id, Long step) {
        return userTaskRepository.findUserTasksStory(id, step);
    }


    public void deleteAllUserTasks() {
        userTaskRepository.deleteAll();
    }

    public UserTask add(UserTask userTask) throws UserTaskException {
        if(userTask == null){
            final String msg = "Error UserTask is null";
            log.error(msg);
            throw new UserTaskException(msg);
        }
        return userTaskRepository.save(userTask);
    }
}
