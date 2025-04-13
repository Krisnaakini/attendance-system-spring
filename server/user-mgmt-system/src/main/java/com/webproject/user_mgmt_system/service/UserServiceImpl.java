package com.webproject.user_mgmt_system.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webproject.user_mgmt_system.entity.User;
import com.webproject.user_mgmt_system.model.LoginRequest;
import com.webproject.user_mgmt_system.model.UserDto;
import com.webproject.user_mgmt_system.repository.UserRepository;
import com.webproject.user_mgmt_system.util.JwtUtility;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserDto saveUser(UserDto user) {
        User user1 = new User();
        BeanUtils.copyProperties(user, user1);
        userRepository.save(user1);
        return user;
    }

    @Override
    public ResponseEntity<?> loginUser(LoginRequest request){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File("src/main/resources/user_details.json");
            JsonNode rootNode = objectMapper.readTree(file);

            for (JsonNode userNode : rootNode.get("users")){
                if (userNode.get("username").asText().equals(request.getUsername()) &&
                        userNode.get("password").asText().equals(request.getPassword())){
                    String token = JwtUtility.generateToken(request.getUsername());
                    return ResponseEntity.status(200).body((Map.of("token", token)));
                }
            }
            return ResponseEntity.status(400).body("Invalid username or password");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}