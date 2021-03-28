package com.chat.HeyYo.converter;

import com.chat.HeyYo.document.ChatRoom;
import com.chat.HeyYo.dto.ChatRoomDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ChatRoomConverter {

    @Autowired
    private ModelMapper mapper;

    public ChatRoomDTO documentToDto(ChatRoom chatRoom) {

        return mapper.map(chatRoom, ChatRoomDTO.class);
    }

    public ChatRoom dtoToDocument(ChatRoomDTO chatRoomDTO) {

        return mapper.map(chatRoomDTO, ChatRoom.class);
    }
}
