package com.restaurant.MiniProjectPhase2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
            "error", "Not Found", "message", ex.getMessage()
    ));
  }

  @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
  public ResponseEntity<?> handleBadRequest(Exception ex) {
    String msg = ex instanceof MethodArgumentNotValidException manv
            ? manv.getBindingResult().getAllErrors().stream()
            .map(ObjectError::getDefaultMessage).collect(java.util.stream.Collectors.joining("; "))
            : ex.getMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
            "error", "Bad Request", "message", msg
    ));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleOther(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "error", "Internal Server Error", "message", ex.getMessage()
    ));
  }
}
