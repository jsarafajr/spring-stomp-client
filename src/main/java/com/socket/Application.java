package com.socket;

import com.socket.stomp.client.StompMessageHandler;
import com.socket.stomp.client.StompSession;
import com.socket.stomp.client.WebSocketStompClient;
import com.socket.stomp.client.simple.SimpleMessageHandler;
import com.socket.stomp.client.simple.SimpleStompClient;
import com.socket.stomp.client.simple.Subscription;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jsarafajr on 04.06.15.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);

        stompSetup();
    }

    public static void stompSetup() throws URISyntaxException {
        SimpleStompClient stompClient = new SimpleStompClient("http://localhost:8080/socket");

        stompClient.connect(new SimpleMessageHandler() {
            @Override
            public void onConnect() {
                subscribe("/topic/client", (session, message) -> {
                    System.out.println(new String(message.getPayload()));
                    session.send("/app/method", "yoyoyooyoy");
                });
            }
        });
    }

}