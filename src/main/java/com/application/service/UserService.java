package com.application.service;

import com.application.dto.UserRegistrationDto;
import com.application.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);

    List<User> findAll();
}
