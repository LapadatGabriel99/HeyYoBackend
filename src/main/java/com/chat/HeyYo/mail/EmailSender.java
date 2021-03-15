package com.chat.HeyYo.mail;

import javax.mail.MessagingException;

public interface EmailSender {

    void send(String to, String message) throws MessagingException;
}
