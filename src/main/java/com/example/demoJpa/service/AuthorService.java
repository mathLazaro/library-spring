package com.example.demoJpa.service;

import com.example.demoJpa.controller.dto.author.AuthorDTO;
import com.example.demoJpa.controller.mapper.AuthorMapper;
import com.example.demoJpa.domain.Author;
import com.example.demoJpa.repository.AuthorRepository;
import com.example.demoJpa.validator.AuthorValidator;
import com.example.demoJpa.validator.UserValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorValidator authorValidator;
    private final UserValidator userValidator;
    private final AuthorMapper authorMapper;

//    public AuthorDTO getAuthorByName(String name) {
//
//    }

    /**
     * @param id id must be passed
     * @return returns Author found on database or null when not found
     * @throws ResponseStatusException throws ResponseStatusException(Not Found) when the id was not found on database
     */
    public AuthorDTO getAuthorById(Integer id) {

        Author response = authorRepository.findById(id).orElse(null);

        authorValidator.verifyNotFound(response);

        return authorMapper.toAuthorDTO(response);
    }

    /**
     * @param name        name can be null if nationality is passed
     * @param nationality nationality can be null if name is passed
     * @return list of authors
     * @throws ResponseStatusException throws ResponseStatusException(Bad Request) if both params are empty
     */
    public List<AuthorDTO> searchAuthorsByNameOrNationality(String name, String nationality) {

        Author authorToSearch = Author.builder().name(name).nationality(nationality).build();
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnorePaths("lastModifiedBy", "createdDate", "lastModifiedDate", "id")
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Author> example = Example.of(authorToSearch, matcher);

        return authorRepository.findAll(example).stream().map(authorMapper::toAuthorDTO).collect(Collectors.toList());
    }

    // restrict methods - need user validation

    /**
     * @param authorDTO authorDTO must be passed
     * @return returns URI of the persisted entity
     * @throws ResponseStatusException throws ResponseStatusException(Unprocessable Entity) if one of the fields from authorDTO is empty ||
     *                                 throws ResponseStatusException(Conflict) when already exists any author with the same name, birthdate and nationality
     *                                 throws ResponseStatusException(Unauthorized) when user is not admin
     *                                 throws ResponseStatusException(Not Found) when user was not found on database
     */
    public Integer persistAuthor(AuthorDTO authorDTO) {

        Author author = authorMapper.toAuthor(authorDTO);
        authorValidator.verifyDuplicatedAuthor(author);

        return authorRepository.save(author).getId();
    }

    /**
     * @param authorId  must be passed
     * @param authorDTO must be passed
     * @throws ResponseStatusException throws ResponseStatusException(Unprocessable Entity) if one of the fields from authorDTO is empty ||
     *                                 throws ResponseStatusException(Conflict) when already exists any author with the same name, birthdate and nationality
     *                                 throws ResponseStatusException(Not Found) when the author was not found on database
     */
    @Transactional

    public void updateAuthor(AuthorDTO authorDTO, Integer authorId) {

        Author authorToUpdate = authorRepository.findById(authorId).orElse(null);
        authorValidator.verifyNotFound(authorToUpdate);
        authorValidator.verifyDuplicatedAuthor(authorToUpdate);


        // updating Author object
        authorToUpdate.setName(authorDTO.name());
        authorToUpdate.setBirthDate(authorDTO.birthDate());
        authorToUpdate.setNationality(authorDTO.nationality());
    }

    /**
     * @param id id must be passed
     * @throws ResponseStatusException throws ResponseStatusException(Bad Request) when the author still has any Book associated
     *                                 throws ResponseStatusException(Not Found) when the author was not found on database
     */
    @Transactional
    public void deleteAuthorById(@NonNull Integer id) {
        Author response = authorRepository.findById(id).orElse(null);

        authorValidator.verifyNotFound(response);
        authorValidator.verifyReferencedAuthor(response);

        authorRepository.deleteById(id);
    }
}
