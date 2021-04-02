package com.chat.HeyYo.repository;

import com.chat.HeyYo.document.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    Optional<ChatRoom> findByRoomName(String roomName);

    @Query(fields = "{'name' : 1, 'nickname': 1}")
    List<ChatRoom> findByRoomOwnerOrderByCreatedAtDesc(String ownerName);
}
