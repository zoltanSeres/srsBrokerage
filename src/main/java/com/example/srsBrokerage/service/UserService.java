package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.user.CreateUserRequest;
import com.example.srsBrokerage.dto.response.user.UserResponse;
import com.example.srsBrokerage.mapper.UserMapper;
import com.example.srsBrokerage.model.User;
import com.example.srsBrokerage.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    public UserRepository getUserRepository() {
        return userRepository;
    }
    public void setUserRepository(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public UserResponse createUser(CreateUserRequest createUserRequest) {

        if (!createUserRequest.firstName().matches("[A-Za-z]+")) {
            throw new IllegalArgumentException("Invalid characters.");
        }

        if (!createUserRequest.lastName().matches("[A-Za-z]+")) {
            throw new IllegalArgumentException("Invalid characters");
        }

        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new IllegalStateException("Email is already used.");
        }


        User user = new User(
                createUserRequest.firstName(),
                createUserRequest.lastName(),
                createUserRequest.email(),
                createUserRequest.password()
        );


        user.isActive();

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
