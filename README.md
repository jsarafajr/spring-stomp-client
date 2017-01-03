# Simple Stomp Client

Spring [STOMP](http://stomp.github.com) client implementation.

## Example

```java
    @SpringBootApplication
    @EnableAutoConfiguration
    public class Application {
    
        public static void main(String[] args) throws Exception {
            SpringApplication.run(Application.class, args);
            
            stompSetup();
        }
    
        public static void stompSetup() throws URISyntaxException {
            SimpleStompClient stompClient = new SimpleStompClient("http://localhost:8080/socket");
    
            stompClient.connect(new SimpleMessageHandler() {
                @Override
                public void onConnect() {
                    // subscribing on events
                    subscribe("/topic/client", (session, message) -> {
                        System.out.println("Got message : " + new String(message.getPayload()));
                        
                        // sending message
                        session.send("/app/method", "Hello Stomp");
                    });
                }
            });
        }
    }
```
