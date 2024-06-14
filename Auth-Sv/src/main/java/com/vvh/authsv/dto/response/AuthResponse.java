package com.vvh.authsv.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthResponse {
    private String userName;
    private String email;
    private String accessToken;
    private String refreshToken;
}
