package ru.platform.learning.mainservice.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.platform.learning.mainservice.entity.Role;
import ru.platform.learning.mainservice.entity.User;
import ru.platform.learning.mainservice.service.RoleService;
import ru.platform.learning.mainservice.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class InitApp {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        List<Role> allRoles = roleService.getAllRoles();
        if (allRoles.isEmpty()){
            List<Role> roles = new ArrayList<Role>(){{
                add(new Role("admin"));
                add(new Role("user"));
            }};

            roleService.addAllRoles(roles);
            log.info("Save list roles");
        }
        List<User> allUsers = userService.getAllUsers();
        log.info("Number of : " + allUsers.size());
        userService.deleteUser(1);
        userService.deleteUser(2);

        if (allUsers.isEmpty() ){
            Role role = roleService.findRoleByName("admin");

            User user = new User();
            user.setUsername("q");
            user.setPassword("q");
            user.setRole(role);
            user.setFirstName("q");
            user.setLastName("q");
            user.setPhone("q");
            user.setDate("Q");
            user.setEmail("q@q");
            user.setEnabled(true);

            userService.addUser(user);

            log.info("Saving new user");
        }
    }
}
