package com.chat.HeyYo.controller;

import com.chat.HeyYo.document.ComplaintCategory;
import com.chat.HeyYo.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/get-complaint-categories")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ComplaintCategory>> getComplaintCategories() {

        return new ResponseEntity<>(complaintService.getCategories(), HttpStatus.ACCEPTED);
    }
}
