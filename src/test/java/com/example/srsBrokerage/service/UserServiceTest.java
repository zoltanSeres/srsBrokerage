package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.user.CreateUserRequest;
import com.example.srsBrokerage.dto.response.user.UserResponse;
import com.example.srsBrokerage.exceptions.UserAlreadyExistsException;
import com.example.srsBrokerage.exceptions.UserNotFoundException;
import com.example.srsBrokerage.mapper.UserMapper;
import com.example.srsBrokerage.model.User;
import com.example.srsBrokerage.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    LocalDateTime timeForTesting = LocalDateTime.now();
    // timeForTesting is used only for testing


    @Test
    void createUser_shouldReturnUser_whenValidInput() {
        CreateUserRequest createUserRequest = new CreateUserRequest("John", "Doe", "john@gmail.com", "123456789");

        User userEntity = new User("John", "Doe", "john@gmail.com", "123456789");
        User savedUser = new User("John", "Doe", "john@gmail.com", "123456789");

        UserResponse userResponse = new UserResponse(1L,"John", "Doe", "john@gmail.com", timeForTesting, timeForTesting);

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


    @Test
    void findUserById_shouldReturnUser_whenValidInput() {
        User user = new User("John", "Doe", "john@gmail.com", "123456789");
        user.setId(1L); //set ID manually for test

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user))
                .thenReturn(new UserResponse(1L,"John", "Doe", "john@gmail.com", timeForTesting, timeForTesting));

        UserResponse result = userService.findUserById(1L);


        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());
        assertEquals("john@gmail.com", result.email());

        verify(userRepository).findById(1L);
    }


    @Test
    void findAllUsers_shouldReturnUsers_whenValidInput() {
        List<User> users = new ArrayList<>();

        User userOne = new User("John", "Doe", "john@gmail.com", "123456789");
        userOne.setId(1L);
        User userTwo = new User("Jane", "Kent", "jane@gmail.com", "987654321");
        userTwo.setId(2L);
        User userThree = new User("Ana", "Mike", "ana@gmail.com", "1020304050");
        userThree.setId(3L);

        users.add(userOne);
        users.add(userTwo);
        users.add(userThree);

        when(userRepository.findAll()).thenReturn(users);

        when(userMapper.toDto(userOne))
                .thenReturn(new UserResponse(1L,"John", "Doe", "john@gmail.com", timeForTesting, timeForTesting));
        when(userMapper.toDto(userTwo))
                .thenReturn(new UserResponse(1L,"Jane", "Kent", "jane@gmail.com", timeForTesting, timeForTesting));
        when(userMapper.toDto(userThree))
                .thenReturn(new UserResponse(1L,"Ana", "Mike", "ana@gmail.com", timeForTesting, timeForTesting));

        List<UserResponse> responses = userService.findAllUsers();

        assertEquals(3, responses.size());
        assertEquals("Ana", responses.get(2).firstName());

        verify(userRepository).findAll();
    }


    @Test
    void updateUserById_shouldUpdateUser_whenValidInput() {
        User user = new User("John", "Doe", "john@gmail.com", "123456789");
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        CreateUserRequest updateUserRequest = new CreateUserRequest("John", "Miller", "john@gmail.com", "123456789");

        user.setFirstName(updateUserRequest.firstName());
        user.setLastName(updateUserRequest.lastName());
        user.setEmail(updateUserRequest.email());
        user.setPasswordHash(updateUserRequest.password());

        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user))
                .thenReturn(new UserResponse(1L,"John", "Miller", "john@gmail.com", timeForTesting, timeForTesting));

        UserResponse result = userService.updateUser(1L, updateUserRequest);

        assertEquals("John", result.firstName());
        assertEquals("Miller", result.lastName());
        assertEquals("john@gmail.com", result.email());

        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
        verify(userMapper).toDto(user);


    }


    @Test
    void deleteUserById_shouldDeleteUser_whenValidInput() {
        User user = new User("John", "Doe", "john@gmail.com", "123456789");
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

       userService.deleteUser(1L);

       verify(userRepository).findById(1L);
       verify(userRepository).delete(user);
    }


    @Test
    void findUserById_ShouldThrowException_whenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUserById(1L));
    }


    @Test
    void createUser_ShouldThrowException_whenUserExists() {
        CreateUserRequest createUserRequest = new CreateUserRequest(
                "John",
                "Doe",
                "john@gmail.com",
                "23456789"
        );

        when(userRepository.existsByEmail("john@gmail.com")).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(createUserRequest));
    }
}
