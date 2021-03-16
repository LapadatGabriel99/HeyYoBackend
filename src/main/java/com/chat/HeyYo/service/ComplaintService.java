package com.chat.HeyYo.service;

import com.chat.HeyYo.document.Complaint;
import com.chat.HeyYo.document.ComplaintCategory;
import com.chat.HeyYo.repository.ComplaintCategoryRepository;
import com.chat.HeyYo.repository.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private ComplaintCategoryRepository complaintCategoryRepository;

    public void registerComplaint(Complaint complaint) {

        if (complaint.getReason() == null || complaint.getReason().isEmpty()) {

            var complaints =  complaint.getCategories()
                    .stream()
                    .map(c -> " " + c.getName() + ",")
                    .reduce((s1, s2) -> s1 + s2)
                    .orElseThrow()
                    .replaceAll("[,]$", "");

            complaint.setReason("User was accused of the following complaints:" + complaints);
        }

        complaintRepository.insert(complaint);
    }

    public List<ComplaintCategory> getCategories() {

        return complaintCategoryRepository.findAll(Sort.by("name").ascending());
    }
}
