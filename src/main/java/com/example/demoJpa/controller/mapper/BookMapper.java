package com.example.demoJpa.controller.mapper;

import com.example.demoJpa.controller.dto.book.BookInputDTO;
import com.example.demoJpa.controller.dto.book.BookOutputDTO;
import com.example.demoJpa.domain.Book;
import com.example.demoJpa.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected AuthorMapper authorMapper;

    @Mapping(target = "author",
            expression = "java(bookInput.authorId() != null ? authorRepository.findById(bookInput.authorId()).orElse(null) : null)")
    public abstract Book  toBook(BookInputDTO bookInput);

    @Mapping(target = "author",
            expression = "java(book.getAuthor() != null? authorMapper.toAuthorDTO(book.getAuthor()) : null)")
    public abstract BookOutputDTO toBookOutputDTO(Book book);
}

