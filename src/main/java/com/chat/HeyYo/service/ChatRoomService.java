package com.chat.HeyYo.service;

import com.chat.HeyYo.document.ChatRoom;
import com.chat.HeyYo.document.Message;
import com.chat.HeyYo.exception.DataNotFoundException;
import com.chat.HeyYo.repository.ChatRoomRepository;
import com.chat.HeyYo.repository.ChatRoomRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatRoomRepositoryImpl chatRoomRepositoryImpl;

    public ChatRoom getChatRoom(String roomName) {

        return chatRoomRepository.findByRoomName(roomName)
                .orElseThrow(() -> new DataNotFoundException(
                        ChatRoom.class.getName(),
                        ChatRoom.class.getDeclaredFields()[1].getName(),
                        roomName));
    }

    public List<ChatRoom> getOwnerChatRooms(String ownerName) {

        return chatRoomRepository.findByRoomOwnerOrderByCreatedAtDesc(ownerName);
    }

    public ChatRoom createChatRoom(ChatRoom chatRoom){

        return chatRoomRepository.insert(chatRoom);
    }

    public void saveMessage(String roomId, Message message) {

        chatRoomRepositoryImpl.updateMessageList(roomId, message);
    }
}
