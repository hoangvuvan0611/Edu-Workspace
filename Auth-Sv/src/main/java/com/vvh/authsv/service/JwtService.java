package com.vvh.authsv.service;

import com.vvh.authsv.constant.TypeToken;
import org.springframework.security.core.Authentication;

public interface JwtService {
    public String generateToken(Authentication authentication, TypeToken tokenType);
    public boolean validateToken(String aToken);
}
