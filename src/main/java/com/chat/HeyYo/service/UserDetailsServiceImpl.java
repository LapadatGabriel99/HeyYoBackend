package com.chat.HeyYo.service;

import com.chat.HeyYo.document.ConfirmationToken;
import com.chat.HeyYo.document.User;
import com.chat.HeyYo.repository.UserRepository;
import com.chat.HeyYo.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findByUsername(username)
                                               .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));

        return UserDetailsImpl.build(user);
    }

    public String handleConfirmationToken(User user) {

        var token = UUID.randomUUID().toString();

        var confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(60),
                user
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public void enableUser(String email) {

        userRepositoryImpl.enableUser(email);
    }

    public void updatePassword(String username, String password) {

        userRepositoryImpl.updatePassword(username, password);
    }

    public void lockUser(String username) {

        userRepositoryImpl.lockUser(username);
    }
}
