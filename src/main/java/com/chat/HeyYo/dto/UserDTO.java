package com.chat.HeyYo.dto;

import com.chat.HeyYo.document.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private String id;

    @Size(min = 1, max = 60, message = "{user.property.size}")
    private String firstname;

    @Size(min = 1, max = 60, message = "{user.property.size}")
    private String lastname;

    @NotBlank(message = "{user.property.notnull}")
    @Size(min = 5, max = 60, message = "{user.property.size}")
    private String username;

    @NotBlank(message = "{user.property.notnull}")
    @Size(min = 5, max = 60, message = "{user.property.size}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Email(message = "{user.email.valid}")
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime registrationDate;

    private boolean enabled;

    private Set<Role> roles;
}
