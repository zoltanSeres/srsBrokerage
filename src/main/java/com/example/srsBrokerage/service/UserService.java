package com.example.srsBrokerage.service;

import com.example.srsBrokerage.config.PasswordConfig;
import com.example.srsBrokerage.config.UserDetailsAdapter;
import com.example.srsBrokerage.dto.request.user.CreateUserRequest;
import com.example.srsBrokerage.dto.response.user.UserResponse;
import com.example.srsBrokerage.exceptions.AccessForbiddenException;
import com.example.srsBrokerage.exceptions.UserAlreadyExistsException;
import com.example.srsBrokerage.exceptions.UserNotFoundException;
import com.example.srsBrokerage.mapper.UserMapper;
import com.example.srsBrokerage.model.User;
import com.example.srsBrokerage.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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

        String hashed = passwordEncoder.encode(createUserRequest.password());

        User user = userMapper.toEntity(createUserRequest);

        user.setPasswordHash(hashed);
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER"); //default
        user.setRoles(roles);

        user.setActive(true);

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }


    public UserResponse findUserById(Long id, Authentication auth) {

        UserDetailsAdapter userDetailsAdapter = (UserDetailsAdapter) auth.getPrincipal();

        Long loggedUserId = userDetailsAdapter.getUserId();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !loggedUserId.equals(id)) {
            throw new AccessForbiddenException("You cannot access this user.");
        }

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
