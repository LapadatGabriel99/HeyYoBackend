package com.chat.HeyYo.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document("messages")
public class Message {

    private MessageType type;

    private String content;

    private String sender;

    private String time;
}
