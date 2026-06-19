package com.as.service;

import com.as.request.LoginRequest;
import com.as.request.RegisterRequest;
import com.as.response.LoginResponse;
import com.as.response.RegisterResponse;
import jakarta.validation.Valid;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
