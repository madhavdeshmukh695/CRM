package com.as.service;

import com.as.entity.Role;
import com.as.entity.RoleType;
import com.as.entity.User;
import com.as.exception.InvalidCredentialsException;
import com.as.exception.UserAlreadyExistsException;
import com.as.mapper.UserMapper;
import com.as.repository.RoleRepository;
import com.as.repository.UserRepository;
import com.as.request.LoginRequest;
import com.as.request.RegisterRequest;
import com.as.response.LoginResponse;
import com.as.response.RegisterResponse;
import com.as.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    @Override
    public RegisterResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("User already exists with this email : "+ request.email());
        }

        User user = mapper.toEntity(request);
        user.setPassword(
                passwordEncoder.encode(request.password()));

        Role role = roleRepository.findByRoleName(RoleType.ROLE_ADMIN)
                .orElseThrow(()->
                        new RuntimeException("Role not Found"));
        user.setRoles(Set.of(role));
        User savedUser = userRepository.save(user);
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                "User Register successfully..!");
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid Credentials"));

        String token = jwtUtil.generateToken(user);

        return new LoginResponse(
                token,
                "Bearer"
        );
    }
}
