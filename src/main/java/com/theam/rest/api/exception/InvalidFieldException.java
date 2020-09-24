package com.theam.rest.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class InvalidFieldException extends RuntimeException {
    public InvalidFieldException() {
        super();
    }

    public InvalidFieldException(String message) {
        super(message);
    }
}
