package com.vvh.authsv.controller;

import com.vvh.authsv.dto.request.AuthRequest;
import com.vvh.authsv.dto.request.RegisterUserRequest;
import com.vvh.authsv.dto.response.AuthResponse;
import com.vvh.authsv.dto.response.ResponseData;
import com.vvh.authsv.dto.response.ResponseError;
import com.vvh.authsv.exception.AlreadyExistsException;
import com.vvh.authsv.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(method = "POST", summary = "Register new user", description = "Send a request via this api to register new user")
    @PostMapping("/register")
    public ResponseData<?> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        log.info(request.getUserName());
        try{
            authService.registerUser(request);
            return new ResponseData<>(true ,HttpStatus.CREATED.value(), "User registered successfully");
        } catch (AlreadyExistsException ex){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseData<?> authUser(@Valid @RequestBody AuthRequest request){
        log.info(request.getUserName());
        try{
            AuthResponse authResponse = authService.authUser(request);
            return new ResponseData<>(true, HttpStatus.OK.value(), "User logged in successfully", authResponse);
        } catch (Exception ex) {
            return new ResponseError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        }
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<?> validateToken(@PathVariable String token){
        return ResponseEntity.ok(authService.validateToken(token));
    }


}
