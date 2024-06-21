package org.vvh.usersv.dto.request;

public record SignUpRequest (
        String username,
        String email,
        String password
){
}
