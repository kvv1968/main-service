package ru.platform.learning.mainservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.learning.mainservice.entity.User;
import ru.platform.learning.mainservice.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }


    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    public User addUser(User user) {
        return userRepository.save(user);
    }


    public User updateUser(User user) {
        return userRepository.save(user);
    }


    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }


    public User getUserByUsername(String username) { return  userRepository.findUserByUsername(username);
    }


    public List<User> getSomeFieldsEmpty() {
        return userRepository.findAllByFirstNameIsNullAndLastNameIsNullAndEmailIsNullAndPhoneIsNull();
    }


}
