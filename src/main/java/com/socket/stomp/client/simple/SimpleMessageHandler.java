package com.socket.stomp.client.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jsarafajr on 08.06.15.
 */
public abstract class SimpleMessageHandler {
    private Map<String, Subscription> subscriptions = new HashMap<>();

    public abstract void onConnect();

    public void subscribe(String destination, Subscription subscription) {
        subscriptions.put(destination, subscription);
    }

    Map<String, Subscription> getSubscriptions() {
        return subscriptions;
    }
}
