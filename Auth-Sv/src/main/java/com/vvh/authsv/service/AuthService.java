package com.vvh.authsv.service;

import com.vvh.authsv.dto.request.AuthRequest;
import com.vvh.authsv.dto.request.RegisterUserRequest;
import com.vvh.authsv.dto.response.AuthResponse;

public interface AuthService {
    void registerUser(RegisterUserRequest request);
    AuthResponse authUser(AuthRequest request);
    boolean validateToken(String token);
}
