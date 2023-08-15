package com.spa.tacocloud.service;

import com.spa.tacocloud.model.User;
import com.spa.tacocloud.security.UserPrinciple;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {
    User toUser(UserPrinciple userDetails);
    UserPrinciple toUserDetails(User user);
}
