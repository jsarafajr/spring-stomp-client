package com.socket;

import com.socket.stomp.client.StompMessageHandler;
import com.socket.stomp.client.StompSession;
import com.socket.stomp.client.WebSocketStompClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
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
        List<Transport> transports = new ArrayList<>();

        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockJsClient = new SockJsClient(transports);

        // app endpoint
        URI uri = new URI("http://localhost:8080/socket");

        WebSocketStompClient stompClient =
                new WebSocketStompClient(uri, new WebSocketHttpHeaders(), sockJsClient);


        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompClient.connect(new StompMessageHandler() {
            @Override
            public void afterConnected(StompSession session, StompHeaderAccessor headers) {
                System.out.println("Success");

                session.subscribe("/topic/client");
            }

            @Override
            public void handleMessage(Message<byte[]> message) {
                System.out.println("message");
            }

            @Override
            public void handleReceipt(String receiptId) {
                System.out.println("receiptId");
            }

            @Override
            public void handleError(Message<byte[]> message) {

            }

            @Override
            public void afterDisconnected() {
                System.out.println("Disconnected");
            }
        });
    }

}