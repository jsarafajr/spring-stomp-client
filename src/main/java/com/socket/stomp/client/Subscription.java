package com.socket.stomp.client;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Created by jsarafajr on 08.06.15.
 */
public interface Subscription {
    void action(WebSocketSession session, TextMessage textMessage);
}
