package com.project.windservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class CurrentGenerationException extends RuntimeException{
    public CurrentGenerationException(String message) {
        super(message);
    }
}
