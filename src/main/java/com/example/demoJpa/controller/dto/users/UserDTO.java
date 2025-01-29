package com.example.demoJpa.controller.dto.users;

import com.example.demoJpa.domain.RoleUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Min(8)
    private String password;
    private String name;
    @NotNull
    private RoleUser roleUser;
}
