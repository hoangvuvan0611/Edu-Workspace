package com.vvh.coresv.controller;

import com.vvh.coresv.dto.request.RegisterUserRequest;
import com.vvh.coresv.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/core/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registerUser(@Valid @RequestBody RegisterUserRequest request){
        log.info("New register: {}", request.getUserName());
        userService.registerUser(request);
    }
}
