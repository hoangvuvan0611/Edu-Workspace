package org.vvh.usersv.dto.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDTO {
    private Long id;
    private String username;
    private String password;
    private String role;
}
