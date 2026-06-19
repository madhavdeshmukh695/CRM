package com.as.response;

public record LoginResponse(
        String accessToken,
        String tokenType
) {
}
