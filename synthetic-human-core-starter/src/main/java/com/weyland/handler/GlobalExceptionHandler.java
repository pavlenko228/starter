package com.weyland.handler;

import java.util.concurrent.RejectedExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.weyland.domain.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("VALIDATION_ERROR", errorMsg));
    }

    @ExceptionHandler(RejectedExecutionException.class)
    public ResponseEntity<ErrorResponse> handleQueueFull() {
        return ResponseEntity.status(429)
            .body(new ErrorResponse("QUEUE_FULL", "Command queue overflow"));
    }
}