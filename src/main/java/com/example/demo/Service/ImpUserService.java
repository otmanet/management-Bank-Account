package com.example.demo.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.UserRegisterService;
import com.example.demo.model.Role;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ImpUserService implements UserServer {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public ImpUserService(@Lazy UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public User save(UserRegisterService userRegisterService) {
        // TODO Auto-generated method stub
        PasswordEncoder passEncoder = passwordEncoder();
        User user = new User(userRegisterService.getEmail(),
                passEncoder.encode(userRegisterService.getPassword()),
                true,
                userRegisterService.getUsername(),
                Arrays.asList(new Role("USER")));
        return userRepository.save(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }

}
