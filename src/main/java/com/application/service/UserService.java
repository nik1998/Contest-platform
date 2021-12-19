package com.application.service;

import com.application.dto.EditUserDto;
import com.application.dto.UserRegistrationDto;
import com.application.model.Contest;
import com.application.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    Optional<User> findById(Long id);

    User save(UserRegistrationDto registration, String role);

    User update(EditUserDto registration);

    List<User> findAll();

    User updateImage(User user, byte[] file);

    User follow(Contest contest, User user);
}
