package com.example.demoJpa.controller.dto.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DefaultRequestException extends RuntimeException {
    private final Integer status;
    private final String message;
    private final List<RuntimeException> errors;
}
