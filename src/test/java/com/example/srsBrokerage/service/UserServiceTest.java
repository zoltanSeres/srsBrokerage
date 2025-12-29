package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.user.CreateUserRequest;
import com.example.srsBrokerage.dto.response.user.UserResponse;
import com.example.srsBrokerage.mapper.UserMapper;
import com.example.srsBrokerage.model.User;
import com.example.srsBrokerage.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldReturnUser_whenValidInput() {
        CreateUserRequest createUserRequest = new CreateUserRequest("John", "Doe", "john@gmail.com", "123456789");

        User userEntity = new User("John", "Doe", "john@gmail.com", "123456789");
        User savedUser = new User("John", "Doe", "john@gmail.com", "123456789");


        LocalDateTime timeForTesting = LocalDateTime.now();
        // timeForTesting is used only for testing
        UserResponse userResponse = new UserResponse("John", "Doe", "john@gmail.com", timeForTesting, timeForTesting);

        when(userMapper.toEntity(createUserRequest)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(userResponse);

        UserResponse result = userService.createUser(createUserRequest);

        assertNotNull(result);
        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());
        assertEquals("john@gmail.com", result.email());

        verify(userRepository).save(any(User.class));
    }
}
