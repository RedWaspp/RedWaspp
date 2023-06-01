package com.ford.cvas.evap.purge.exception;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class ApiException {
    private final ArrayList<String> message;

    private final ZonedDateTime timeStamp;

    public ArrayList<String> getMessage() {
        return message;
    }


    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    //public ApiException(String message, Throwable throwable, HttpStatus httpStatus, ZonedDateTime timeStamp) {
    public ApiException(String message, ZonedDateTime timeStamp) {

        this.message = new ArrayList<String>();
        this.message.add(message);
        this.timeStamp = timeStamp;
    }

    public ApiException(ArrayList<String> message, ZonedDateTime timeStamp) {

        this.message = message;
        this.timeStamp = timeStamp;
    }

    public ApiException(String[] message) {
        this.message = new ArrayList<>(Arrays.asList(message));
        this.timeStamp = ZonedDateTime.now(ZoneId.of("Z"));
    }

}
