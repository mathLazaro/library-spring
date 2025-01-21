package com.example.demoJpa.controller.mapper;

import com.example.demoJpa.controller.dto.author.AuthorDTO;
import com.example.demoJpa.domain.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toAuthor(AuthorDTO authorDTO);
    AuthorDTO toAuthorDTO(Author author);

}
