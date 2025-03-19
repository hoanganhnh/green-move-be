package com.rimmelasghar.boilerplate.springboot.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {
    private final String errorMessage;
}
