package com.project.batteryservice.exception;

public class BatteryNotFoundException extends RuntimeException{
    public BatteryNotFoundException(String message) {
        super(message);
    }
}
