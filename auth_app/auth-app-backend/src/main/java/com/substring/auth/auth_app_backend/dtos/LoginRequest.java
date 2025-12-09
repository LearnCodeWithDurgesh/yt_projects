package com.substring.auth.auth_app_backend.dtos;

public record LoginRequest(
        String email,
        String password
) {
}
