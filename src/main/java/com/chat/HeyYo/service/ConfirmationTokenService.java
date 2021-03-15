package com.chat.HeyYo.service;

import com.chat.HeyYo.document.ConfirmationToken;
import com.chat.HeyYo.repository.ConfirmationTokenRepository;
import com.chat.HeyYo.repository.ConfirmationTokenRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepositoryImpl confirmationTokenRepositoryImpl;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {

        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token) {

        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmedAt(String token) {

        confirmationTokenRepositoryImpl.updateConfirmAt(token, LocalDateTime.now());
    }
}
