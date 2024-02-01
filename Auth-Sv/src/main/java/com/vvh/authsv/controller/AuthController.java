package com.vvh.authsv.controller;

import com.vvh.authsv.request.AuthRequest;
import com.vvh.authsv.request.RegisterUserRequest;
import com.vvh.authsv.response.AuthResponse;
import com.vvh.authsv.response.BaseResponse;
import com.vvh.authsv.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> registerUser(@Valid @RequestBody RegisterUserRequest request){
        authService.registerUser(request);
        return ResponseEntity.ok(new BaseResponse(true));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authUser(@Valid @RequestBody AuthRequest request){
        return ResponseEntity.ok(authService.authUser(request));
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<?> validateToken(@PathVariable String token){
        return ResponseEntity.ok(authService.validateToken(token));
    }
}
