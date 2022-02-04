package ru.platform.learning.mainservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.platform.learning.mainservice.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRole(String name);


}
