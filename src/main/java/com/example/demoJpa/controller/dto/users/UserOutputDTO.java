package com.example.demoJpa.controller.dto.users;

import com.example.demoJpa.domain.RoleUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserOutputDTO {
    private String name;
    private String username;
    private String email;
    private RoleUser roleUser;
}
