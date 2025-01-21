package com.example.demoJpa.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AuthorDTO(Integer id,
                        @NotBlank(message = "nome obrigatório")
                        String name,
                        @NotNull(message = "data de nascimento obrigatório")
                        LocalDate birthDate,
                        @NotBlank(message = "nacionalidade obrigatória")
                        String nationality) {

}
