package org.vvh.usersv.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vvh.usersv.dto.model.UserAuthDTO;
import org.vvh.usersv.dto.model.UserDTO;
import org.vvh.usersv.dto.request.SignUpRequest;
import org.vvh.usersv.entity.Role;
import org.vvh.usersv.entity.User;
import org.vvh.usersv.enums.RoleEnum;
import org.vvh.usersv.exception.UserNotFoundException;
import org.vvh.usersv.repository.RoleRepository;
import org.vvh.usersv.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public void signUp(SignUpRequest request) {
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName(RoleEnum.ANONYMOUS);
        roles.add(role);

        role.getUsers().add(user);
        user.setRoles(roles);

        roleRepository.save(role);
        userRepository.save(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return modelMapper.map(findUserById(id), UserDTO.class);
    }

    @Override
    public UserAuthDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        return modelMapper.map(user, UserAuthDTO.class);
    }

    @Override
    public Boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
