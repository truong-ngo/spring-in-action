package com.spa.tacocloud.service;

import com.spa.tacocloud.model.Role;
import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    default Role toRole(GrantedAuthority authority) {
        return new Role(authority.getAuthority());
    }

    default GrantedAuthority toAuthority(Role role) {
        return new SimpleGrantedAuthority(role.getName());
    }
}
