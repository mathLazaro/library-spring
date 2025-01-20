package com.example.demoJpa.controller.dto;


import com.example.demoJpa.domain.Author;
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

public Author toAuthor() {

    return Author.builder()
            .name(name)
            .birthDate(birthDate)
            .nationality(nationality)
            .build();
}

public static AuthorDTO toAuthorDTO(Author author) {

    return AuthorDTO.builder()
            .id(author.getId())
            .name(author.getName())
            .birthDate(author.getBirthDate())
            .nationality(author.getNationality())
            .build();
}
}
