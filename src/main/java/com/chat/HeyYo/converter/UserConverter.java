package com.chat.HeyYo.converter;

import com.chat.HeyYo.document.User;
import com.chat.HeyYo.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserConverter {

    @Autowired
    private ModelMapper mapper;

    public UserDTO documentToDto(User user) {

        return mapper.map(user, UserDTO.class);
    }

    public User dtoToDocument(UserDTO userDTO) {

        return mapper.map(userDTO, User.class);
    }
}
