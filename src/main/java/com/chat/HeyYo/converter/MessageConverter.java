package com.chat.HeyYo.converter;

import com.chat.HeyYo.document.Message;
import com.chat.HeyYo.dto.MessageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageConverter {

    @Autowired
    private ModelMapper mapper;

    public MessageDTO documentToDto(Message message) {

        return mapper.map(message, MessageDTO.class);
    }

    public Message dtoToDocument(MessageDTO dto) {

        mapper.typeMap(MessageDTO.class, Message.class)
                .addMapping(MessageDTO::getTime, Message::setTime)
                .addMapping(MessageDTO::getContent, Message::setContent)
                .addMapping(MessageDTO::getSender, Message::setSender)
                .addMapping(MessageDTO::getTime, Message::setTime);

        return mapper.map(dto, Message.class);
    }
}
