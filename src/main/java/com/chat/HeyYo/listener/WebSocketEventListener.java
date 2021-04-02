package com.chat.HeyYo.listener;

import com.chat.HeyYo.document.Message;
import com.chat.HeyYo.document.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;

@Component
public class WebSocketEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messageSendingOperations;

    @EventListener
    public void handleWebSocketConnectListener(final SessionConnectedEvent event) {

        LOGGER.info("Connection received");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(final SessionDisconnectEvent event) {

        final var headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        final var username = (String)headerAccessor.getSessionAttributes().get("username");

        final var message = new Message();
        message.setType(MessageType.DISCONNECT);
        message.setContent(username + " left");
        message.setSender(username);
        message.setTime(LocalDateTime.now().toString());

        messageSendingOperations.convertAndSend("/topic/public", message);
    }
}
