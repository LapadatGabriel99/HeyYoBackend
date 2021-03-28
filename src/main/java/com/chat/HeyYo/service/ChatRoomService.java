package com.chat.HeyYo.service;

import com.chat.HeyYo.document.ChatRoom;
import com.chat.HeyYo.exception.DataNotFoundException;
import com.chat.HeyYo.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public ChatRoom getChatRoom(String roomName) {

        return chatRoomRepository.findByRoomName(roomName)
                .orElseThrow(() -> new DataNotFoundException(
                        ChatRoom.class.getName(),
                        ChatRoom.class.getDeclaredFields()[1].getName(),
                        roomName));
    }

    public List<ChatRoom> getOwnerChatRooms(String ownerName) {

        return chatRoomRepository.findByOwnerNameOrderByCreatedAtDesc(ownerName);
    }
}
