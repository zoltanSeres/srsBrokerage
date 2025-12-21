package com.example.srsBrokerage.dto.response.user;

import java.time.LocalDateTime;

public record UserResponse(
        String firstName,
        String lastName,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
