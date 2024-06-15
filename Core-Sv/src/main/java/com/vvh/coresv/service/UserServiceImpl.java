package com.vvh.coresv.service;

import com.vvh.coresv.dto.model.UserDto;
import com.vvh.coresv.entity.User;
import com.vvh.coresv.enums.Role;
import com.vvh.coresv.repository.UserRepository;
import com.vvh.coresv.dto.request.RegisterUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

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

    @Override
    public UserDto getUserById(Long id) {
        User user = findUserById(id);
        return modelMapper.map(user, UserDto.class);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }
}
