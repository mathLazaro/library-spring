package com.example.demoJpa.controller.dto.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
@Builder
public class InvalidFieldExceptionDTO implements ErrorDetailDTO {

    private final String field;
    private final String error;

    public static ErrorDetailDTO toInvalidFieldExceptionDTO(FieldError e) {
        return InvalidFieldExceptionDTO.builder()
                .field(e.getField())
                .error(e.getDefaultMessage())
                .build();
    }
}
