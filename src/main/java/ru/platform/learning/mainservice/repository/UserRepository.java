package ru.platform.learning.mainservice.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import ru.platform.learning.mainservice.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    User findUserById(Long id);

    User findUserByUsername(String username);
    List<User> findAllByFirstNameIsNullAndLastNameIsNullAndEmailIsNullAndPhoneIsNull();
}
