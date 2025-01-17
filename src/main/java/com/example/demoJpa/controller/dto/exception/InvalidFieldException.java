package com.example.demoJpa.controller.dto.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InvalidFieldException extends RuntimeException {

    private final String field;
    private final String message;
}
