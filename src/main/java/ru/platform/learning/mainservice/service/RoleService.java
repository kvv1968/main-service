package ru.platform.learning.mainservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.platform.learning.mainservice.entity.Role;
import ru.platform.learning.mainservice.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public Role addRole(Role role) {
        return roleRepository.save(role);
    }


    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }


    public List<Role> addRoles(List<Role> roles) {
        return roleRepository.saveAll(roles);
    }


    public Role findRoleByName(String name) {
        return roleRepository.findRoleByRole(name);
    }

    public void deleteRole(Role role){
        roleRepository.delete(role);
    }
}
