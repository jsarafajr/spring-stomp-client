package com.socket.stomp.client;


public interface StompClient {

	void connect(StompMessageHandler messageHandler);

}
