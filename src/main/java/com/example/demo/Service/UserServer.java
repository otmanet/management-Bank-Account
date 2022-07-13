package com.example.demo.Service;

import com.example.demo.dto.UserRegisterService;
import com.example.demo.model.User;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserServer extends UserDetailsService {
    /*
     * UserDetailsService :Core interface which loads user-specific data. It is used
     * throughout the framework
     * as a user DAO and is the strategy used by the DaoAuthenticationProvider .
     */
    User save(UserRegisterService userRegisterService);
}
