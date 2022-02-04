package ru.platform.learning.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.platform.learning.mainservice.entity.UserTask;
import ru.platform.learning.mainservice.model.LessonTopic;

import java.util.List;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {

    @Query("SELECT ut FROM UserTask ut WHERE ut.user.id = :id AND ut.templates.lessonTopic = :lessonTopic")
    List<UserTask> findUserTasksByUserAndLessonTopic(@Param("id") Long id, @Param("lessonTopic") LessonTopic lessonTopic);

    @Query("SELECT ut FROM UserTask ut WHERE ut.user.id = :id AND ut.templates.id = :idTask")
    List<UserTask> findUserTasksStory(@Param("id") Long id, @Param("idTask") Long idTask);

}
