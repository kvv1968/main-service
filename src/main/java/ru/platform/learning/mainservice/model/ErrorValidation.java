package ru.platform.learning.mainservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorValidation {
    private String message;
    private String nameField;

    @Override
    public String toString() {
        return "{'message':'" + message + "', 'field':'" + nameField + "'}";
    }
}
