package com.example.demoJpa.controller.dto;


import com.example.demoJpa.domain.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record AuthorDTO(Integer id,
                        @NotBlank(message = "mensagem obrigatória")
                        String name,
                        @NotNull(message = "mensagem obrigatória")
                        LocalDate birthDate,
                        @NotBlank(message = "mensagem obrigatória")
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
