package com.as.response;

public record RegisterResponse(
        Long id,
        String email,
        String message
) {
}
