package com.project.distributionservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class DistributionException extends RuntimeException{
    public DistributionException(String message) {
        super(message);
    }
}
