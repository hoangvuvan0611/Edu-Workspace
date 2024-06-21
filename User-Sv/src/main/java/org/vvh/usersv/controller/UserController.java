package org.vvh.usersv.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.vvh.usersv.dto.model.UserAuthDTO;
import org.vvh.usersv.dto.request.SignUpRequest;
import org.vvh.usersv.dto.response.ResponseData;
import org.vvh.usersv.dto.response.ResponseError;
import org.vvh.usersv.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public void signUp(@RequestBody SignUpRequest request) {
        log.info("Sign up request: {}", request);
        try {
            userService.signUp(request);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseData<?> getUserById(@PathVariable String id) {
        log.info("Get user by id: {}", id);
        try {
          return new ResponseData<>(HttpStatus.OK.value(), "Get user with id " + id, "userService.getUserById(id)");
        } catch (Exception ex) {
            log.error("Get user with id {} failed", id, ex);
            return new ResponseError(HttpStatus.NOT_FOUND.value(), "Get user with id " + id + " failed");
        }
    }

    @PostMapping("/username")
    public UserAuthDTO getUserByUsername(@RequestBody String username) {
        log.info("Get user by username {}", username);
        try {
            return userService.getUserByUsername(username);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @GetMapping("/username/exist/{username}")
    public Boolean usernameExist(@PathVariable String username) {
        return userService.existsUserByUsername(username);
    }

    @GetMapping("/email/exist/{email}")
    public Boolean emailExist(@PathVariable String email) {
        return userService.existsUserByEmail(email);
    }
}
