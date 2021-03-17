package com.chat.HeyYo.controller;

import com.chat.HeyYo.document.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ChatController {

    @MessageMapping("/chat.join")
    @SendTo("/topic/public")
    public Message join(@Payload final Message message, SimpMessageHeaderAccessor headerAccessor) {

        headerAccessor.getSessionAttributes().put("username", message.getSender());

        return message;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public Message send(@Payload final Message message) {

        return message;
    }
}
