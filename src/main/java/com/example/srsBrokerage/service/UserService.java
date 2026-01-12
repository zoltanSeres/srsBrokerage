package com.example.srsBrokerage.service;

import com.example.srsBrokerage.dto.request.user.CreateUserRequest;
import com.example.srsBrokerage.dto.response.user.UserResponse;
import com.example.srsBrokerage.exceptions.UserAlreadyExistsException;
import com.example.srsBrokerage.exceptions.UserNotFoundException;
import com.example.srsBrokerage.mapper.UserMapper;
import com.example.srsBrokerage.model.User;
import com.example.srsBrokerage.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(CreateUserRequest createUserRequest) {

        if (!createUserRequest.firstName().matches("[A-Za-z]+")) {
            throw new IllegalArgumentException("Invalid characters.");
        }

        if (!createUserRequest.lastName().matches("[A-Za-z]+")) {
            throw new IllegalArgumentException("Invalid characters");
        }

        if (userRepository.existsByEmail(createUserRequest.email())) {
            throw new UserAlreadyExistsException("User with email" + createUserRequest.email() + " is already used.");
        }

        User user = userMapper.toEntity(createUserRequest);

        user.isActive();

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }


    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + "not found."));

        return userMapper.toDto(user);
    }


    public List<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }


    public UserResponse updateUser(Long id, CreateUserRequest createUserRequest) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + "not found."));

        if (!createUserRequest.firstName().matches("[A-Za-z]+")) {
            throw new IllegalArgumentException("Invalid characters");
        }
        if (!createUserRequest.lastName().matches("[A-Za-z]+")) {
            throw new IllegalArgumentException("Invalid characters");
        }
        if (userRepository.existsByEmailAndIdNot(createUserRequest.email(), userToUpdate.getId())) {
            throw new UserAlreadyExistsException("User with email " + createUserRequest.email() + " is already used.");
        }

        userToUpdate.setFirstName(createUserRequest.firstName());
        userToUpdate.setLastName(createUserRequest.lastName());
        userToUpdate.setEmail(createUserRequest.email());

        User updatedUser = userRepository.save(userToUpdate);

        return userMapper.toDto(updatedUser);
    }


    public void deleteUser(Long id) {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + "not found."));
        userRepository.delete(userToDelete);
    }
}
