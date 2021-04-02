package com.chat.HeyYo.controller;

import com.chat.HeyYo.converter.MessageConverter;
import com.chat.HeyYo.document.Message;
import com.chat.HeyYo.dto.MessageDTO;
import com.chat.HeyYo.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WebSocketController {

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private MessageConverter messageConverter;

    @MessageMapping("/chat.join")
    @SendTo("/topic/public")
    public Message join(@Payload final MessageDTO dto, SimpMessageHeaderAccessor headerAccessor) {

        headerAccessor.getSessionAttributes().put("username", dto.getSender());

        var message = messageConverter.dtoToDocument(dto);

        chatRoomService.saveMessage(dto.getRoomId(), message);

        return message;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public Message send(@Payload final MessageDTO dto) {

        var message = messageConverter.dtoToDocument(dto);

        chatRoomService.saveMessage(dto.getRoomId(), message);

        return message;
    }
}
