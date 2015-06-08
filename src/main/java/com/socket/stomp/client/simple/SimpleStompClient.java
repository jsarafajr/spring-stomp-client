package com.socket.stomp.client.simple;

import com.socket.stomp.client.StompClient;
import com.socket.stomp.client.StompMessageHandler;
import com.socket.stomp.client.StompSession;
import com.socket.stomp.client.WebSocketStompClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
import java.util.Map;

/**
 * Created by jsarafajr on 08.06.15.
 */
public class SimpleStompClient {
    final Logger LOG = LogManager.getLogger(SimpleStompClient.class);

    private WebSocketStompClient stompClient;

    private String url;

    public SimpleStompClient(String url) throws URISyntaxException {
        this.url = url;

        List<Transport> transports = new ArrayList<>();

        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockJsClient = new SockJsClient(transports);

        // app endpoint
        URI uri = new URI(url);

        stompClient =
                new WebSocketStompClient(uri, new WebSocketHttpHeaders(), sockJsClient);


        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    public void connect(SimpleMessageHandler handler) {
        stompClient.connect(new StompMessageHandler() {
            @Override
            public void afterConnected(StompSession session, StompHeaderAccessor headers) {
                LOG.info("Socket handshake with [" + url + "] success.");

                handler.onConnect();

                Map<String, Subscription> subscriptions = handler.getSubscriptions();

                for (Map.Entry<String, Subscription> entry : subscriptions.entrySet()) {
                    session.subscribe(entry.getKey());
                }
            }

            @Override
            public void handleMessage(StompSession session, Message<byte[]> message) {
                String destination = StompHeaderAccessor.wrap(message).getDestination();

                Subscription subscription = handler.getSubscriptions().get(destination);

                if (subscription != null) {
                    LOG.info("Handled message from [" + destination + "]");
                    subscription.action(session, message);
                }
            }

            @Override
            public void handleError(Message<byte[]> message) {
                LOG.error("Error: " + message.getHeaders());
            }

            @Override
            public void afterDisconnected() {
                LOG.error("Disconnected from [" + url + "].");
            }
        });
    }
}
