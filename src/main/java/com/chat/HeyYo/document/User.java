package com.chat.HeyYo.document;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document("users")
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
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
    private String password;

    @Email(message = "{user.email.valid}")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime registrationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime lastLoginDate;

    private boolean enabled;

    private boolean locked;

    private Set<Role> roles;

    private Set<ConfirmationToken> confirmationTokens;
}
