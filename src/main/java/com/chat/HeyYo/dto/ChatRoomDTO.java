package com.chat.HeyYo.dto;

import com.chat.HeyYo.document.Message;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

public class ChatRoomDTO {

    private String id;

    @NotBlank(message = "{chatRoom.name.notnull}")
    @Size(min = 1, max = 1024, message = "{chatRoom.name.size}")
    private String roomName;

    private String roomNickname;

    private String roomOwner;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    private Set<Message> listOfMessages;

    private Set<String> listOfMembers;
}
