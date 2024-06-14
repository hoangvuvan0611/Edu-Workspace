package com.vvh.authsv.service;

import com.vvh.authsv.constant.TypeToken;
import com.vvh.authsv.entity.User;
import com.vvh.authsv.exception.AlreadyExistsException;
import com.vvh.authsv.repository.UserCredentialRepository;
import com.vvh.authsv.dto.request.AuthRequest;
import com.vvh.authsv.dto.request.RegisterUserRequest;
import com.vvh.authsv.dto.response.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService{

    @Value("${templateUrl}")
    private String baseUrl;

    private final UserCredentialRepository userCredentialRepository;

    private final AuthenticationManager authenticationManager;

    private final RestTemplate restTemplate;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserCredentialRepository userCredentialRepository, AuthenticationManager authenticationManager, RestTemplate restTemplate, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userCredentialRepository = userCredentialRepository;
        this.authenticationManager = authenticationManager;
        this.restTemplate = restTemplate;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(RegisterUserRequest request) {
        if(userCredentialRepository.existsByEmail(request.getEmail()))
            throw new AlreadyExistsException("Email already exists!");

        if(userCredentialRepository.existsByUserName(request.getUserName()))
            throw new AlreadyExistsException("Username already exists!");

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        restTemplate.postForObject(baseUrl, request, RegisterUserRequest.class);
    }

    @Override
    public AuthResponse authUser(AuthRequest request) {
        Authentication authentication = authentication(request.getUserName(), request.getPassword());
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
                .email(user.getEmail())
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
