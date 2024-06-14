package com.vvh.coresv.service;

import com.vvh.coresv.entity.User;
import com.vvh.coresv.enums.Role;
import com.vvh.coresv.repository.UserRepository;
import com.vvh.coresv.dto.request.RegisterUserRequest;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(RegisterUserRequest request) {
        User user = User.builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }
}
