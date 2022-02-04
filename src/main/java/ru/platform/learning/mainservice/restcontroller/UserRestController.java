package ru.platform.learning.mainservice.restcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.platform.learning.mainservice.entity.Role;
import ru.platform.learning.mainservice.entity.User;
import ru.platform.learning.mainservice.service.RoleService;
import ru.platform.learning.mainservice.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@Slf4j
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception ex) {
        final String msg = ex.getMessage();
        log.error(msg);
        return ResponseEntity.status(500).body(msg);
    }
}




