package ru.platform.learning.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.platform.learning.mainservice.entity.TaskTemplate;
import ru.platform.learning.mainservice.model.LessonTopic;

import java.util.List;


public interface TaskRepository extends JpaRepository<TaskTemplate, Long> {

    List<TaskTemplate>findTaskTemplateByLessonTopic(LessonTopic lessonTopic);

    TaskTemplate findTaskTemplateByLessonTopicAndId(LessonTopic lessonTopic, Long id);

    TaskTemplate findTaskTemplateByNameClass(String nameClass);

}
