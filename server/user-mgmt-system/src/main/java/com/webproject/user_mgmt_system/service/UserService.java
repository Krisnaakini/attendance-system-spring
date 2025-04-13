package com.webproject.user_mgmt_system.service;

import com.webproject.user_mgmt_system.model.LoginRequest;
import com.webproject.user_mgmt_system.model.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService{
    UserDto saveUser(UserDto user);

    ResponseEntity<?> loginUser(LoginRequest request);
}
