package com.socket.stomp.client;


import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;


public interface StompMessageHandler {

	void afterConnected(StompSession session, StompHeaderAccessor headers);

	void handleMessage(StompSession session, Message<byte[]> message);

	void handleError(Message<byte[]> message);

	void afterDisconnected();

}
