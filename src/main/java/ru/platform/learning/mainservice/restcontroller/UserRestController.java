package ru.platform.learning.mainservice.restcontroller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.platform.learning.mainservice.entity.Role;
import ru.platform.learning.mainservice.entity.User;
import ru.platform.learning.mainservice.model.MailLetter;
import ru.platform.learning.mainservice.service.EmailService;
import ru.platform.learning.mainservice.service.RoleService;
import ru.platform.learning.mainservice.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@RestController
@Slf4j
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;
    private final EmailService emailService;

    @Value("${main.base-url}")
    private String baseUrl;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public UserRestController(UserService userService, RoleService roleService, EmailService emailService) {
        this.userService = userService;
        this.roleService = roleService;
        this.emailService = emailService;
    }


    @GetMapping("/admin/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/user/profile")
    public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        User user = (User) principal.getPrincipal();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin/users/roles")
    public ResponseEntity<?> getRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping("/admin/users")
    public ResponseEntity<?> createUser(User user, @RequestParam(name = "role") String role) {
        Role rolle = roleService.findRoleByName(role);
        user.setRole(rolle);
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PutMapping("/user/users")
    public ResponseEntity<?> updateUser(User user, @RequestParam(name = "role") String role) {
        Role rolle = roleService.findRoleByName(role);
        user.setRole(rolle);
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @PostMapping("/admin/users/delete")
    public void deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/user/form-reg")
    public ResponseEntity<?> userFormReg(@ModelAttribute User user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) request.getUserPrincipal();
        User userRepo = (User) principal.getPrincipal();
        userRepo.setUsername(user.getUsername());
        userRepo.setPassword(user.getPassword());
        userRepo.setFirstName(user.getFirstName());
        userRepo.setLastName(user.getLastName());
        userRepo.setEmail(user.getEmail());
        userRepo.setPhone(user.getPhone());
        if (user.getDate() == null){
            userRepo.setDate(new Date().toString());
        } else {
            userRepo.setDate(user.getDate());
        }
        userRepo.setEnabled(true);

        return ResponseEntity.ok(userService.updateUser(userRepo));
    }

    @PostMapping("/user/mail-send")
    public ResponseEntity<?> sendMail(MailLetter mailLetter) throws IOException {
        if (mailLetter == null) {
            final String msg = "Error MailLetter is null";
            log.error(msg);
            return ResponseEntity.status(400).body(msg);
        }
        User techUser = createTechUser(mailLetter.getMessage());

        mailLetter.setMessage(String.format("Логин -> %s\r\n\r\nПароль -> %s\r\n\r\nВременный логин и пароль " +
                "действует в течении суток,\r\nне забудьте авторизироваться по адрессу %s", techUser.getUsername(), techUser.getPassword(), baseUrl));

        try {
            emailService.sendMail(mailLetter.getSubject(), mailLetter.getMessage(), mailLetter.getEmail());

            userService.addUser(techUser);

            log.info("Send email techUser {}", mailLetter.getEmail());

            return ResponseEntity.ok("Письмо отправлено");

        } catch (MailSendException ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.ok(String.format("Письмо не отправлено \r\n%s", ex));
        }
    }

    private User createTechUser(String message) throws IOException {
        JsonNode node = mapper.readTree(message.getBytes(StandardCharsets.UTF_8));
        User user = new User();
        user.setUsername(node.findValue("username").textValue());
        user.setPassword(node.findValue("password").textValue());
        user.setEnabled(true);
        Role role = roleService.findRoleByName("user");
        user.setRole(role);
        return user;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception ex) {
        final String msg = ex.getMessage();
        log.error(msg);
        return ResponseEntity.status(500).body(msg);
    }
}




