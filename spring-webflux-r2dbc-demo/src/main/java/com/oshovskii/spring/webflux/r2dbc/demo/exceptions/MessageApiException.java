package com.oshovskii.spring.webflux.r2dbc.demo.exceptions;

public class MessageApiException extends Exception {
    public MessageApiException(String message) {
        super(message);
    }
}
