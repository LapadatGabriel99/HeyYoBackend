package com.chat.HeyYo.controller;

import com.chat.HeyYo.converter.ChatRoomConverter;
import com.chat.HeyYo.dto.ChatRoomDTO;
import com.chat.HeyYo.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0/chat-room")
@CrossOrigin
public class ChatController {

    @Autowired
    private ChatRoomConverter chatRoomConverter;

    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping("/create-chat-room")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ChatRoomDTO> createChatRoom(@RequestBody ChatRoomDTO dto) {

        return new ResponseEntity<>(
                chatRoomConverter.documentToDto(
                        chatRoomService.createChatRoom(chatRoomConverter.dtoToDocument(dto))),
                HttpStatus.CREATED);
    }

    @GetMapping("/get-chat-room-details")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ChatRoomDTO> getChatRoomDetails(@RequestParam("name") String name){

        return new ResponseEntity<>(
                chatRoomConverter.documentToDto(chatRoomService.getChatRoom(name)),
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-user-rooms")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ChatRoomDTO>> getUserChatRooms(@RequestParam("name") String name) {

        return new ResponseEntity<>(
                chatRoomService.getOwnerChatRooms(name)
                        .stream()
                        .map(chatRoom -> chatRoomConverter.documentToDto(chatRoom))
                        .collect(Collectors.toList()),
                HttpStatus.ACCEPTED);
    }
}
