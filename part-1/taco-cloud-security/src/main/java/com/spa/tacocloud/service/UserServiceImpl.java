package com.spa.tacocloud.service;

import com.spa.tacocloud.model.RegistrationForm;
import com.spa.tacocloud.model.Role;
import com.spa.tacocloud.model.User;
import com.spa.tacocloud.repository.RoleRepository;
import com.spa.tacocloud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Arrays;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final String USER_ROLE = "ROLE_USER";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return userMapper.toUserDetails(user);
    }

    @Override
    public void save(RegistrationForm form) {
        User user = form.toUser(passwordEncoder);
        Role role = roleRepository.findByName(USER_ROLE);
        user.setRoles(List.of(role));
        userRepository.save(user);
    }
}
