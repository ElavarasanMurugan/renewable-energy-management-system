package com.project.solarservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class SolarPanelNotFoundException extends RuntimeException{
    public SolarPanelNotFoundException(String message) {
        super(message);
    }
}
