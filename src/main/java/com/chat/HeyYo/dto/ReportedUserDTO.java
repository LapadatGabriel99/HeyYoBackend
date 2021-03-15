package com.chat.HeyYo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReportedUserDTO {

    @NotBlank
    @NotBlank(message = "{user.property.notnull}")
    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime issuedAtDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime reportedAtDate;

    private String complaint;

    private boolean locked;
}
