package com.chat.HeyYo.converter;

import com.chat.HeyYo.document.Complaint;
import com.chat.HeyYo.dto.ComplaintDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ComplaintConverter {

    @Autowired
    private ModelMapper mapper;

    public ComplaintDTO complaintToDto(Complaint complaint) {

        return mapper.map(complaint, ComplaintDTO.class);
    }

    public Complaint dtoToComplaint(ComplaintDTO dto) {

        return mapper.map(dto, Complaint.class);
    }
}
