package com.chat.HeyYo.dto;

import com.chat.HeyYo.document.Message;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

@Getter
@Setter
public class ChatRoomDTO {

    private String id;

    @NotBlank(message = "{chatRoom.name.notnull}")
    @Size(min = 1, max = 1024, message = "{chatRoom.name.size}")
    private String roomName;

    private String roomNickname;

    private String roomOwner;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    private List<Message> listOfMessages;

    private TreeSet<String> listOfMembers;
}
