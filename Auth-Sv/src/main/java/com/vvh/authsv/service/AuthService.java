package com.vvh.authsv.service;

import com.vvh.authsv.request.AuthRequest;
import com.vvh.authsv.request.RegisterUserRequest;
import com.vvh.authsv.response.AuthResponse;

public interface AuthService {
    void registerUser(RegisterUserRequest request);
    AuthResponse authUser(AuthRequest request);
    boolean validateToken(String token);
}
