package ru.platform.learning.mainservice.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import ru.platform.learning.mainservice.entity.User;
import ru.platform.learning.mainservice.exception.UserTaskException;
import ru.platform.learning.mainservice.model.ErrorValidation;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class UserValidator {
    //TODO: добавить паттерны для валидации
    public Set<ErrorValidation> validate(User user) throws UserTaskException {
        if (user == null){
            final String msg = "Error user is null";
            log.error(msg);
            throw new UserTaskException(msg);
        }
        final String msg = "Error validation user field={} is null";
        Set<ErrorValidation> validations = new HashSet<>();
        if (user.getId() == null){
            final String nameField = "id";
            log.error(msg, nameField);
            validations.add(new ErrorValidation(msg, nameField));
        }
        if (StringUtils.isEmpty(user.getUsername())){
            final String nameField = "username";
            log.error(msg, nameField);
            validations.add(new ErrorValidation(msg, nameField));
        }
        if (StringUtils.isEmpty(user.getPassword())){
            final String nameField = "password";
            log.error(msg, nameField);
            validations.add(new ErrorValidation(msg, nameField));
        }
        if (StringUtils.isEmpty(user.getFirstName())){
            final String nameField = "firstName";
            log.error(msg, nameField);
            validations.add(new ErrorValidation(msg, nameField));
        }
        if (StringUtils.isEmpty(user.getLastName())){
            final String nameField = "lastName";
            log.error(msg, nameField);
            validations.add(new ErrorValidation(msg, nameField));
        }
        if (StringUtils.isEmpty(user.getEmail())){
            final String nameField = "email";
            log.error(msg, nameField);
            validations.add(new ErrorValidation(msg, nameField));
        }
        if (StringUtils.isEmpty(user.getPhone())){
            final String nameField = "phone";
            log.error(msg, nameField);
            validations.add(new ErrorValidation(msg, nameField));
        }
        if (!user.getEnabled()){
            final String nameField = "enabled";
            final String msgEnabled = "Error user field={}, value={}";
            log.error(msgEnabled, nameField, user.getEnabled());
            validations.add(new ErrorValidation(msg, nameField));
        }
        return validations;
    }


}
