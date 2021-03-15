package com.chat.HeyYo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResetPasswordDTO {

    private boolean isAuthorized;

    private String role;
}
