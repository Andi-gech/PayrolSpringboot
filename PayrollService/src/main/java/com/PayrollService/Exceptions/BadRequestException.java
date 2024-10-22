package com.PayrollService.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST) // This sends a 400 response
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
