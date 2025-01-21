package com.example.demoJpa.controller.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookInputDTO(
        @NotBlank(message = "ISBN é obrigatório")
        @ISBN(message = "Tipo ISBN deve ser fornecido")
        @Length(message = "Tamanho excedido",max = 255)
        String isbn,
        @NotBlank(message = "Titulo é obrigatório")
        @Length(message = "Tamanho excedido",max = 512)
        String title,
        @NotNull(message = "Data de publicação é obrigatória")
        @Past(message = "Data de publicação deve ser data passada")
        LocalDate publishDate,
        @Length(message = "Tamanho excedido",max = 255)
        String genre,
        BigDecimal price,
        Integer authorId
) {

}
