package com.example.demoJpa.controller.dto.users;

import com.example.demoJpa.domain.RoleUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String name;
    @NotNull
    private RoleUser roleUser;
}
