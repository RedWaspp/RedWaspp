package com.ford.cvas.evap.purge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Set;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleApiRequestException(ConstraintViolationException ae) {
        // 1. Create payload containing exception details
        // 2. Return response entity
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        Set<ConstraintViolation<?>> validations = ae.getConstraintViolations();
        ApiException apiException = new ApiException(
            ae.getMessage(),  ZonedDateTime.now(ZoneId.of("Z"))
        );
        ArrayList<String> errors = new ArrayList<>();
        for(ConstraintViolation<?> validation : validations) {
           errors.add(String.format(validation.getMessageTemplate(), validation.getInvalidValue()));
           apiException = new ApiException(
              errors ,  ZonedDateTime.now(ZoneId.of("Z"))
            );

        }


        return new ResponseEntity<>(apiException, badRequest);
    }


    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException ae) {
        // 1. Create payload containing exception details
        // 2. Return response entity
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
           ae.getErrors(),  ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, badRequest);
    }



    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<Object> handleApiRequestException(MissingServletRequestParameterException ae) {
        // 1. Create payload containing exception details
        // 2. Return response entity
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
            ae.getMessage(),  ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleApiRequestException(NotFoundException ae) {
        // 1. Create payload containing exception details
        // 2. Return response entity
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
            ae.getMessage(),

            ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, badRequest);
    }
}
