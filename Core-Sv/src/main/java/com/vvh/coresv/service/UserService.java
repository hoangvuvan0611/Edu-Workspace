package com.vvh.coresv.service;

import com.vvh.coresv.dto.model.UserDto;
import com.vvh.coresv.dto.request.RegisterUserRequest;
import com.vvh.coresv.entity.User;

import java.util.Optional;

public interface UserService {
    void registerUser(RegisterUserRequest request);
    UserDto getUserById(Long id);
}
