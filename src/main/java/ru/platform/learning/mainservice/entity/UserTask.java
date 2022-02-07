package ru.platform.learning.mainservice.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "users_tasks")
@DynamicUpdate
public class UserTask implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="text")
    private String answer;
    @Column(columnDefinition="text")
    private String message;

    private Boolean isResultTask;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="templates_id", nullable=false)
    private TaskTemplate templates;

    public UserTask() {
        super();
    }
}
