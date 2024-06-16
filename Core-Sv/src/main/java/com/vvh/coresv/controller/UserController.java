package com.vvh.coresv.controller;

import com.vvh.coresv.dto.request.RegisterUserRequest;
import com.vvh.coresv.dto.response.ResponseData;
import com.vvh.coresv.entity.User;
import com.vvh.coresv.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseData<?> getUser(@PathVariable Long id) {
        log.info("Get user: {}", id);
        try{
            return new ResponseData<>(HttpStatus.OK.value(), "Get user successfully", userService.getUserById(id));
        } catch (Exception ex) {

        }
        return null;
    }
}
