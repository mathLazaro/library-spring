package com.example.demoJpa.dto;


import com.example.demoJpa.domain.Author;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record AuthorDTO(Integer id,
                        String name,
                        LocalDate birthDate,
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
