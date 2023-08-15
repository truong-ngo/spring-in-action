package com.spa.tacocloud.service;

import com.spa.tacocloud.model.RegistrationForm;
import com.spa.tacocloud.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(RegistrationForm form);
}
