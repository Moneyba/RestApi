package com.theam.rest.api.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ExceptionResponse
{
    public ExceptionResponse(String message, LocalDateTime dateTime, List<String> details) {
        this.message = message;
        this.dateTime = dateTime;
        this.details = details;
    }

    private String message;

    private LocalDateTime dateTime;

    private List<String> details;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
