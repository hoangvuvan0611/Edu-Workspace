package com.vvh.coresv.request;

import com.vvh.coresv.constant.ValidateAttribute;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {

    @NotBlank(message = "Name cannot be blank")
    private String userName;

    @Email(regexp = ValidateAttribute.EMAIL_REGEX, message = "Invalid email!")
    private String email;

    @Pattern(regexp = ValidateAttribute.PASSWORD_REGEX, message = "Minimum 8, maximum 100 characters")
    private String password;
}
