package com.example.demoJpa.exception.handler;

import com.example.demoJpa.controller.dto.exception.DefaultRequestExceptionDTO;
import com.example.demoJpa.controller.dto.exception.ErrorDetailDTO;
import com.example.demoJpa.controller.dto.exception.InvalidFieldExceptionDTO;
import com.example.demoJpa.exception.InvalidCheckException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class DefaultExceptionHandler {

    private ResponseEntity<DefaultRequestExceptionDTO> wrapException(String message, List<ErrorDetailDTO> listErrors, HttpStatus status) {

        DefaultRequestExceptionDTO exception = DefaultRequestExceptionDTO
                .builder()
                .message(message)
                .status(status.value())
                .errors(listErrors)
                .build();

        return new ResponseEntity<>(exception, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus
    public ResponseEntity<DefaultRequestExceptionDTO> handleFieldConstraintViolation(MethodArgumentNotValidException e) {

        List<ErrorDetailDTO> errors = e.getFieldErrors().stream().map(InvalidFieldExceptionDTO::toInvalidFieldExceptionDTO).toList();

        return wrapException("Violação de campo de inserção", errors, HttpStatus.UNPROCESSABLE_ENTITY);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DefaultRequestExceptionDTO> handleDuplicatedEntityInput(DataIntegrityViolationException e) {

        return wrapException(e.getMessage(), List.of(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({EntityNotFoundException.class,UsernameNotFoundException.class})
    public ResponseEntity<DefaultRequestExceptionDTO> handleEntityNotFound(Exception e) {

        return wrapException(e.getMessage(), List.of(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(PermissionDeniedDataAccessException.class)
    public ResponseEntity<DefaultRequestExceptionDTO> handleUnauthorizedRole(PermissionDeniedDataAccessException e) {

        return wrapException(e.getMessage(), List.of(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidCheckException.class)
    public ResponseEntity<DefaultRequestExceptionDTO> handleInvalidCheck(InvalidCheckException e) {

        return wrapException(e.getMessage(), List.of(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DefaultRequestExceptionDTO> handleGenericExceptions(Exception e) {

        return wrapException(e.getMessage(), List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<DefaultRequestExceptionDTO> handleAuthentitcationException(AuthenticationException e) {
        return wrapException("Senha ou usuário inválidos", List.of(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<DefaultRequestExceptionDTO> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        return wrapException("Acesso negado", List.of(), HttpStatus.FORBIDDEN);
    }
}
