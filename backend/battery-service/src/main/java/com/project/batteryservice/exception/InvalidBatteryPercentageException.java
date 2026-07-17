package com.project.batteryservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class InvalidBatteryPercentageException extends RuntimeException{
    public InvalidBatteryPercentageException(String message) {
        super(message);
    }
}
