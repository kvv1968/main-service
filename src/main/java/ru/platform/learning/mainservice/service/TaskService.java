package ru.platform.learning.mainservice.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.learning.mainservice.entity.TaskTemplate;
import ru.platform.learning.mainservice.model.LessonTopic;
import ru.platform.learning.mainservice.repository.TaskRepository;

import java.util.List;

@Service
@Slf4j
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<TaskTemplate> getAllTaskTemplates() {
        return taskRepository.findAll();
    }


    public TaskTemplate createTaskTemplate(TaskTemplate taskTemplate) {
        return taskRepository.saveAndFlush(taskTemplate);
    }

    public List<TaskTemplate> createListTaskTemplates(List<TaskTemplate> taskTemplates) {
        return taskRepository.saveAll(taskTemplates);
    }


    public TaskTemplate updateTaskTemplate(TaskTemplate taskTemplate) {
        return taskRepository.save(taskTemplate);
    }


    public void deleteTaskTemplate(Long id) {
        taskRepository.deleteById(id);
    }



    public TaskTemplate findTaskTemplateById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }


    public void deleteAll() {
        taskRepository.deleteAll();
    }


    public List<TaskTemplate> findTaskTemplateByLessonTopic(LessonTopic lessonTopic) {
        return taskRepository.findTaskTemplateByLessonTopic(lessonTopic);
    }

    public TaskTemplate findTaskTemplateByNameClass(String nameClass){
        if (StringUtils.isEmpty(nameClass)){
            final String msg = "Error nameClass is empty";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return taskRepository.findTaskTemplateByNameClass(nameClass);
    }


}
