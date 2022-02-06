package ru.platform.learning.mainservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompilerTask {

    private String nameClass;
    private String answer;
    private byte[] bytes;
    private Boolean isQuestion;
    private String correctAnswers;
}
