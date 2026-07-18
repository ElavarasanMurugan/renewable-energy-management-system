package com.project.distributionservice.exception;

import com.project.distributionservice.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation error",
                message,
                LocalDateTime.now(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
