package com.as.mapper;

import com.as.entity.User;
import com.as.request.RegisterRequest;
import com.as.response.RegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequest request);
    RegisterResponse toResponse(User user);
}
