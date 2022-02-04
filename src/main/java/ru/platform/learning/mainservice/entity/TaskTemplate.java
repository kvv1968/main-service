package ru.platform.learning.mainservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.platform.learning.mainservice.model.LessonTopic;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks_templates", uniqueConstraints =
        {
                @UniqueConstraint(columnNames = "id"),
                @UniqueConstraint(columnNames = "nameTask")
        }
)
@JsonIgnoreProperties("userTasks")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskTemplate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LessonTopic lessonTopic;

    private String nameTask;

    @Column(length = 1024)
    private String description;

    private String nameClass;

    @Column(length = 3000)
    private String res;

    private Boolean  isQuestion;

    @Column(length = 1024)
    private String correctAnswers;

    @Transient
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "java_file_id", referencedColumnName = "id")
    private JavaFile javaFile;

    @Transient
    @OneToMany(mappedBy="templates", fetch = FetchType.EAGER)
    private Set<UserTask> userTasks;

}


