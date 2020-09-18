package com.theam.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UniqueConstraintException extends RuntimeException {
    public UniqueConstraintException() {
        super();
    }

    public UniqueConstraintException(String message) {
        super(message);
    }
}
