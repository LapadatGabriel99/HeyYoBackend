package com.chat.HeyYo.dto;

import com.chat.HeyYo.document.ComplaintCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

public class ComplaintDTO {

    @NotBlank(message = "{complaint.issuedBy.notnull}")
    private String issuedBy;

    @NotBlank(message = "{complaint.issuedAt.notnull}")
    private String issuedAgainst;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime issuedAtDate;

    private String reason;

    @NotBlank(message = "{complaint.categories.notnull}")
    private Set<ComplaintCategory> categories;
}
