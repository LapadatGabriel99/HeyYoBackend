package com.chat.HeyYo.controller;

import com.chat.HeyYo.dto.ReportedUserDTO;
import com.chat.HeyYo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private UserService userService;

    @PutMapping("/lock-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReportedUserDTO> lockUserAccount(@Valid @RequestBody ReportedUserDTO dto) {

        userService.lockUserAccount(dto.getUsername());

        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @PutMapping("/unlock-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReportedUserDTO> unlockUserAccount(@Valid @RequestBody ReportedUserDTO dto) {

        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }
}
