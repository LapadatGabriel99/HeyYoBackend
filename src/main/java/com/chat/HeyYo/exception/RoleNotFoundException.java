package com.chat.HeyYo.exception;

import com.chat.HeyYo.document.ERole;

public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(ERole role) {

        super(String.format("%s was not found", role));
    }
}
