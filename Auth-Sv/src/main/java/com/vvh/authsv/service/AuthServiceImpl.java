package com.vvh.authsv.service;

import com.vvh.authsv.constant.TypeToken;
import com.vvh.authsv.dto.request.AuthRequest;
import com.vvh.authsv.dto.request.RegisterUserRequest;
import com.vvh.authsv.dto.response.AuthResponse;
import com.vvh.authsv.exception.AlreadyExistsException;
import com.vvh.authsv.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    @Value("${templateSignUpUrl}")
    private String signUpUrl;

    private final AuthenticationManager authenticationManager;

    private final RestTemplate restTemplate;

    private final JwtService jwtService;

    @Override
    public void registerUser(RegisterUserRequest request) {
        String url = signUpUrl + "/username/exist/" + request.getUsername();
        Boolean usernameExists = restTemplate.getForObject(url, Boolean.class);
        if(usernameExists) {
            throw new AlreadyExistsException("username already exists");
        }
        url = signUpUrl + "/email/exist/" + request.getEmail();
        boolean emailExists = Boolean.TRUE.equals(
                restTemplate.getForObject(url, Boolean.class));
        if(emailExists) {
            throw new AlreadyExistsException("email already exists");
        }
        restTemplate.postForObject(signUpUrl, request, RegisterUserRequest.class);
    }

    @Override
    public AuthResponse authUser(AuthRequest request) {
        Authentication authentication = authentication(request.getUsername(), request.getPassword());
        if(!authentication.isAuthenticated())
            throw new RuntimeException("invalid access!");

        return getAuthResponse(authentication);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    private AuthResponse getAuthResponse(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateToken(authentication, TypeToken.ACCESS);
        String refreshToken = jwtService.generateToken(authentication, TypeToken.REFRESH);

        return AuthResponse.builder()
                .userName(user.getUsername())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Authentication authentication(String username, String password){
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
    }
}
