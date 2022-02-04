package ru.platform.learning.mainservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class MailLetter implements Serializable {
    private String subject;
    private String email;
    private String message;
}
