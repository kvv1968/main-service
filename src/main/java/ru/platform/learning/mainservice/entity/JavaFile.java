package ru.platform.learning.mainservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "java_file")
public class JavaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameClass;

    private byte[]bytes;

    @OneToOne
    @MapsId
    @JoinColumn(name = "tasktemplate_id")
    private TaskTemplate taskTemplate;

    public JavaFile() {
    }
}
