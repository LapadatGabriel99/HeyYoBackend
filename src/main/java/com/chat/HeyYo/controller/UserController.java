package com.chat.HeyYo.controller;

import com.chat.HeyYo.converter.ComplaintConverter;
import com.chat.HeyYo.converter.UserConverter;
import com.chat.HeyYo.dto.ComplaintDTO;
import com.chat.HeyYo.dto.PasswordRecoveryDTO;
import com.chat.HeyYo.dto.ResetPasswordDTO;
import com.chat.HeyYo.dto.UserDTO;
import com.chat.HeyYo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private ComplaintConverter complaintConverter;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody UserDTO userDTO, HttpServletResponse response) {

        return new ResponseEntity<>(userConverter.documentToDto(
                userService.login(userConverter.dtoToDocument(userDTO), response)),
                HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDTO) throws MessagingException {

        return new ResponseEntity<>(userConverter.documentToDto(
                userService.register(userConverter.dtoToDocument(userDTO))),
                HttpStatus.CREATED);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {

        return userService.confirmToken(token, false);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response) {

        userService.logout(response);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<PasswordRecoveryDTO> forgotPassword(@Valid @RequestBody PasswordRecoveryDTO dto) throws MessagingException {

        var user = userService.sendPasswordRecoveryMail(dto.getEmail());
        dto.setUsername(user);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/reset-password")
    public ResponseEntity<ResetPasswordDTO> resetPassword(@RequestParam("token") String token) {

        userService.confirmToken(token, true);

        return new ResponseEntity<>(new ResetPasswordDTO(true, "GUEST"), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update-password")
    public ResponseEntity updatePassword(@Valid @RequestBody UserDTO userDTO) {

        userService.updatePassword(userConverter.dtoToDocument(userDTO));

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/report-user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity reportUser(@Valid @RequestBody ComplaintDTO complaintDTO) {

        userService.reportUser(complaintConverter.dtoToComplaint(complaintDTO));

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
