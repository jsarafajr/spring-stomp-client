package com.socket.stomp.client.simple;

import com.socket.stomp.client.StompSession;
import org.springframework.messaging.Message;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by jsarafajr on 08.06.15.
 */
public interface Subscription {
    void action(StompSession session, Message<byte[]> message);
}
