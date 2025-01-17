package com.example.demoJpa.service;

import com.example.demoJpa.domain.Author;
import com.example.demoJpa.controller.dto.AuthorDTO;
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

    /**
     * @param id id must be passed
     * @return returns Author found on database or null when not found
     * @throws ResponseStatusException throws ResponseStatusException(Not Found) when the id was not found on database
     */
    public AuthorDTO getAuthorById(Integer id) throws ResponseStatusException {

        Author response = authorRepository.findById(id).orElse(null);

        authorValidator.runValidation(
                response,
                List.of(authorValidator::verifyNotFound));

        return AuthorDTO.toAuthorDTO(response);
    }

    /**
     * @param name        name can be null if nationality is passed
     * @param nationality nationality can be null if name is passed
     * @return list of authors
     * @throws ResponseStatusException throws ResponseStatusException(Bad Request) if both params are empty
     */
    public List<AuthorDTO> searchAuthorsByNameOrNationality(String name, String nationality) throws ResponseStatusException {

        // TODO - corrigir retorno inesperado
        System.out.println(name);
        System.out.println(nationality);
        Author authorToSearch = Author.builder().name(name).nationality(nationality).build();
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnorePaths("last_modified_date", "created_date")
//                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Author> example = Example.of(authorToSearch, matcher);

        return authorRepository.findAll(example).stream().map(AuthorDTO::toAuthorDTO).collect(Collectors.toList());
    }

    // restrict methods - need user validation

    /**
     * @param authorDTO authorDTO must be passed
     * @param userId    user responsible for persist operation
     * @return returns URI of the persisted entity
     * @throws ResponseStatusException throws ResponseStatusException(Unprocessable Entity) if one of the fields from authorDTO is empty ||
     *                                 throws ResponseStatusException(Conflict) when already exists any author with the same name, birthdate and nationality
     *                                 throws ResponseStatusException(Unauthorized) when user is not admin
     *                                 throws ResponseStatusException(Not Found) when user was not found on database
     */
    public Integer persistAuthor(AuthorDTO authorDTO, Integer userId) throws ResponseStatusException {

        userValidator.verifyAdmin(userId);

        authorValidator.runValidation(
                authorDTO.toAuthor(),
                List.of(authorValidator::verifyFields,
                        authorValidator::verifyDuplicatedAuthor));

        return authorRepository.save(authorDTO.toAuthor()).getId();
    }

    /**
     * @param authorId  must be passed
     * @param authorDTO must be passed
     * @throws ResponseStatusException throws ResponseStatusException(Unprocessable Entity) if one of the fields from authorDTO is empty ||
     *                                 throws ResponseStatusException(Conflict) when already exists any author with the same name, birthdate and nationality
     *                                 throws ResponseStatusException(Not Found) when the author was not found on database
     */
    @Transactional

    public void updateAuthor(AuthorDTO authorDTO, Integer authorId, Integer userId) throws ResponseStatusException {

        userValidator.verifyAdmin(userId);

        Author authorToUpdate = authorRepository.findById(authorId).orElse(null);
        authorValidator.runValidation(
                authorToUpdate,
                List.of(authorValidator::verifyNotFound,
                        authorValidator::verifyFields,
                        authorValidator::verifyDuplicatedAuthor));

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
    public void deleteAuthorById(@NonNull Integer id, Integer userId) throws ResponseStatusException {

        userValidator.verifyAdmin(userId);

        Author response = authorRepository.findById(id).orElse(null);

        authorValidator.runValidation(
                response,
                List.of(authorValidator::verifyNotFound,
                        authorValidator::verifyReferencedAuthor));

        authorRepository.deleteById(id);
    }
}
