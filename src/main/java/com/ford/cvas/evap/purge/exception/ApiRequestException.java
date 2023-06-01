package com.ford.cvas.evap.purge.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class ApiRequestException extends RuntimeException {
    private final ArrayList<String> errors;


    public ApiRequestException(String message,ArrayList<String> errors) {
        super(message);
        this.errors = errors;
    }

    public ApiRequestException(String message, Throwable cause,ArrayList<String> errors) {
        super(message, cause);
        this.errors = errors;
    }
    public ApiRequestException(String[] errors) {
        super();
        this.errors = new ArrayList<>(Arrays.asList(errors));
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
        this.errors = new ArrayList<>();
        this.errors.add(message);
    }

    public ApiRequestException(String message) {
        this.errors= new ArrayList<>(Collections.singletonList(message));
    }

    public ArrayList<String> getErrors() {
        return this.errors;
    }

}
