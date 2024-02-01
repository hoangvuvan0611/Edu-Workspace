package com.vvh.authsv.request;

import com.vvh.authsv.constant.ValidateAttribute;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
