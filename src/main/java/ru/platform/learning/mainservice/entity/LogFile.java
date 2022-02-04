package ru.platform.learning.mainservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "log_file")
public class LogFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameFile;

    private byte[]bytes;

    private Date expiredDate;

    public LogFile() {
    }

    public LogFile(String nameFile, byte[] bytes, Date expiredDate) {
        this.nameFile = nameFile;
        this.bytes = bytes;
        this.expiredDate = expiredDate;
    }
}
