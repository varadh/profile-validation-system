package com.quickbooks.profileservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<String> handleResourceNotFoundException(Exception ex, WebRequest request) {
            return new ResponseEntity("Resource not found", HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler({Exception.class})
        public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
            List<String> details = new ArrayList<>();
            details.add(ex.getLocalizedMessage());
            ErrorResponse error = new ErrorResponse("Internal Server Error", details);
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

}
