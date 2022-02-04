package ru.platform.learning.mainservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;
import ru.platform.learning.mainservice.entity.TaskTemplate;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {


    @GetMapping
    public String adminPage(){
        return "admin_page";
    }

    @GetMapping("/users-page")
    public String adminUserPage(){
        return "admin_userlist";
    }

    @GetMapping("/ser/{page}")
    public String openServerPage(@PathVariable String page){
        if (StringUtils.isEmpty(page)){
            final String msg = "Error page is null";
            log.error(msg);
            return "colorlib-error-404-16/index";
        }
        return page;
    }


    @ExceptionHandler(Exception.class)
    public String handlerException(Exception ex, Model model){
        final String msg = ex.getMessage();
        log.error(msg);
        model.addAttribute("error", msg);
        return "colorlib-error-404-16/index";
    }
}
