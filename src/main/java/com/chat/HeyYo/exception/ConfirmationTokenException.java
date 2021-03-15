package com.chat.HeyYo.exception;

public class ConfirmationTokenException extends IllegalStateException{

    public ConfirmationTokenException(String message, String token) {

        super(String.format(message, token));
    }

    public ConfirmationTokenException(String message) {

        super(message);
    }
}
