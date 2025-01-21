package com.example.demoJpa.controller.dto.book;

import com.example.demoJpa.controller.dto.author.AuthorDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookOutputDTO(
        @NotBlank
        @ISBN
        @Length(max = 255)
        String isbn,
        @NotBlank
        @Length(max = 512)
        String title,
        @Past
        @NotNull
        LocalDate publishDate,
        @Length(max = 255)
        String genre,
        BigDecimal price,
        AuthorDTO author
) {

}

