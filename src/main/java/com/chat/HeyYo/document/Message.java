package com.chat.HeyYo.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("messages")
public class Message {

    private MessageType type;

    private String content;

    private String sender;

    private String time;
}
