package com.chat.HeyYo.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Document("complaints")
public class Complaint {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotBlank(message = "{user.property.notnull}")
    private String issuedBy;

    @NotBlank(message = "{user.property.notnull}")
    private String issuedAgainst;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime issuedAtDate;

    private String reason;

    @NotEmpty(message = "{complaint.categories.notnull}")
    private Set<ComplaintCategory> categories;
}
