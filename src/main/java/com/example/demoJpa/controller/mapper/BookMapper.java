package com.example.demoJpa.controller.mapper;

import com.example.demoJpa.controller.dto.book.BookInputDTO;
import com.example.demoJpa.domain.Book;
import com.example.demoJpa.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    protected AuthorRepository authorRepository;
//    @Autowired
//    protected AuthorMapper authorMapper;

    @Mapping(target = "author",
            expression = "java(authorRepository.findById(bookInput.authorId()).orElse(null))")
    public abstract Book toBook(BookInputDTO bookInput);
}

