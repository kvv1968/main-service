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
        List<User> allUsers = userService.getAllUsers();

        for (User user:allUsers){
            userService.deleteUser(user.getId());
        }
        for (Role role:allRoles){
            roleService.deleteRole(role);
        }

        if (allRoles.isEmpty()){
            List<Role> roles = new ArrayList<Role>(){{
                add(new Role("admin"));
                add(new Role("user"));
                add(new Role("ROLE_ADMIN"));
            }};

            roleService.addRoles(roles);
            log.info("Save list roles");
        }

        log.info("Number of : " + allUsers.size());


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

            Role role1 = roleService.findRoleByName("ROLE_ADMIN");
            User user1 = new User();
            user1.setUsername("ADMIN");
            user1.setPassword("c33455d0-d50a-3e8f-a153-b7b05312826d");
            user1.setRole(role1);
            user1.setFirstName("ADMIN");
            user1.setLastName("ADMIN");
            user1.setPhone("ADMIN");
            user1.setDate("ADMIN");
            user1.setEmail("ADMIN@ADMIN");
            user1.setEnabled(true);

            userService.addUser(user1);

            log.info("Saving new user");
        }
    }
}
