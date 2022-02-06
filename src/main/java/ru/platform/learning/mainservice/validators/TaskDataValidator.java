package ru.platform.learning.mainservice.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import ru.platform.learning.mainservice.entity.User;
import ru.platform.learning.mainservice.exception.UserTaskException;
import ru.platform.learning.mainservice.model.ErrorValidation;
import ru.platform.learning.mainservice.model.TaskData;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class TaskDataValidator {
    @Autowired
    private UserValidator userValidator;

    public Set<ErrorValidation> validate(TaskData taskData, User user)
     throws UserTaskException {
        if (taskData == null){
            final String msg = "Error taskData is null";
            log.error(msg);
            throw new UserTaskException(msg);
        }
        final String msg = "Error validation taskData field={} is null";
        Set<ErrorValidation> validations = new HashSet<>();
        if (taskData.getIdTask() == null){
            final String nameField = "idTask";
            log.error(msg, nameField);
            validations.add(new ErrorValidation(msg, nameField));
        }
        if (StringUtils.isEmpty(taskData.getAnswer())){
            final String nameField = "answer";
            log.error(msg, nameField);
            validations.add(new ErrorValidation(msg, nameField));
        }


        validations.addAll(userValidator.validate(user));

        return validations;
    }

}
