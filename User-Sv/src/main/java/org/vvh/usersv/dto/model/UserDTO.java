package org.vvh.usersv.dto.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {
    private long id;
    private String username;
    private String code;
    private String role;
}
