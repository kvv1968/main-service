package ru.platform.learning.mainservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class UserController {

    @GetMapping("/login")
    public String loginPage() {
        return "login_page";
    }

    @GetMapping("/user/nav")
    public String navPage(){
        return "nav_page";
    }

    @GetMapping("/user/nav/lessons/{ordinal}")
    public String lessonsPage(@PathVariable Integer ordinal,  Model model){
        model.addAttribute("data-text", ordinal);
        return "lessons";
    }

    @GetMapping("/user/nav/lessons/step/{step}")
    public String lessonByStepPage(@PathVariable Integer step,  Model model){
        model.addAttribute("data-step", step);
        return "lesson";
    }

    @GetMapping("/user/reg")
    public String registrationPage() {
        return "user_reg";
    }

    @GetMapping("/user/send-email")
    public String sendEmailPage() {
        return "send_email_page";
    }

    @GetMapping("/user/error")
    public String errorPage() {
        return "colorlib-error-404-16/index";
    }

    @GetMapping("/user/exam-date")
    public String examDate() {
        return "exam_date";
    }

    @ExceptionHandler(Exception.class)
    public String handlerException(Exception ex, Model model){
        final String msg = ex.getMessage();
        log.error(msg);
        model.addAttribute("error", msg);
        return "colorlib-error-404-16/index";
    }
}
