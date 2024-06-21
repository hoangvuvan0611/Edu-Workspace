package org.vvh.usersv.service;

import org.vvh.usersv.dto.model.UserAuthDTO;
import org.vvh.usersv.dto.model.UserDTO;
import org.vvh.usersv.dto.request.SignUpRequest;
import org.vvh.usersv.entity.User;

public interface UserService {
    void signUp(SignUpRequest request);
    UserDTO getUserById(Long id);
    UserAuthDTO getUserByUsername(String username);
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);
}
