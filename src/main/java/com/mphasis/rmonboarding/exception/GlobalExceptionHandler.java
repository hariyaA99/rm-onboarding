package com.mphasis.rmonboarding.exception;

import com.mphasis.rmonboarding.dto.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.mphasis.rmonboarding.exception.InvalidCredentialsException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidTokenException(
            InvalidTokenException ex, WebRequest request) {
        logger.error("Invalid token exception: {}", ex.getMessage());

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid Token",
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleSessionNotFoundException(
            SessionNotFoundException ex, WebRequest request) {
        logger.error("Session not found exception: {}", ex.getMessage());

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "Session Not Found",
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponseDTO> handleTokenExpiredException(
            TokenExpiredException ex, WebRequest request) {
        logger.error("Token expired exception: {}", ex.getMessage());

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                "Token Expired",
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDatabaseOperationException(
            DatabaseOperationException ex, WebRequest request) {
        logger.error("Database operation exception: {}", ex.getMessage(), ex);

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Database Operation Failed",
                "An error occurred while processing your request",
                request.getDescription(false),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(
            ValidationException ex, WebRequest request) {
        logger.error("Validation exception: {}", ex.getMessage());

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {
        logger.error("Method argument not valid exception: {}", ex.getMessage());

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "Request validation failed: " + fieldErrors.toString(),
                request.getDescription(false),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        logger.error("Illegal argument exception: {}", ex.getMessage());

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid Request",
                ex.getMessage(),
                request.getDescription(false),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponseDTO> handleGlobalException(
//            Exception ex, WebRequest request) {
//        logger.error("Unexpected exception: {}", ex.getMessage(), ex);
//
//        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                "Internal Server Error",
//                "An unexpected error occurred",
//                request.getDescription(false),
//                LocalDateTime.now()
//        );
//
//        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    //Sign in exceptions below
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", "Unauthorized");
        response.put("message", ex.getMessage());
        response.put("path", "/api/user/validate");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", "Unauthorized");
        response.put("message", ex.getMessage());
        response.put("path", "/api/user/validate");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());
        response.put("path", "N/A");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}