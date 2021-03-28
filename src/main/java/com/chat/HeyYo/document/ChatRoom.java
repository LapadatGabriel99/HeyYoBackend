package com.chat.HeyYo.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Document("rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Indexed(unique = true)
    @Field("name")
    private String roomName;

    @Indexed
    @Field("nickname")
    private String roomNickname;

    @Indexed
    @Field("owner")
    private String roomOwner;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @Field("messages")
    private Set<Message> listOfMessages;

    @Field("members")
    private Set<String> listOfMembers;
}
