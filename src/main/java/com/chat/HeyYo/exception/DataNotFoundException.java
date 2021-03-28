package com.chat.HeyYo.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String ofClass) {

        super(String.format("Object of class: %s not found", ofClass));
    }

    public DataNotFoundException(String ofClass, String hasField, String withValue) {

        super(String.format("Object of class: %s, has field: %s, with value: %s isn't found",
                ofClass, hasField, withValue));
    }
}
