package com.project.windservice.exception;

public class WindTurbineNotFoundException extends RuntimeException{
    public WindTurbineNotFoundException(String message) {
        super(message);
    }
}
