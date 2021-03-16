package com.chat.HeyYo.dto;

import com.chat.HeyYo.document.ComplaintCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class ComplaintDTO {

    @NotBlank(message = "{complaint.issuedBy.notnull}")
    private String issuedBy;

    @NotBlank(message = "{complaint.issuedAt.notnull}")
    private String issuedAgainst;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime issuedAtDate;

    private String reason;

    @NotEmpty(message = "{complaint.categories.notnull}")
    private Set<ComplaintCategory> categories;
}
