package com.financeirosimplificado.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity treatDataIntegrityViolation(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().body("User already exists");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity treatNotFound(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity treatException(Exception e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

}
