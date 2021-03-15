package com.chat.HeyYo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordRecoveryDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String username;

    @NotBlank(message = "{user.property.notnull}")
    @Email(message = "{user.email.valid}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;
}
