package com.socket.stomp.client;


public interface StompSession {

	void subscribe(String destination);

	void send(String destination, Object payload);

	void disconnect();

}
