package com.example.demoJpa.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidCheckException extends RuntimeException {

    private final String message;
}
