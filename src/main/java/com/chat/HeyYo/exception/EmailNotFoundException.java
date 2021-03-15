package com.chat.HeyYo.exception;

public class EmailNotFoundException extends RuntimeException{

    public EmailNotFoundException(String email) {

        super(String.format("There is no user with the email %s", email));
    }
}
