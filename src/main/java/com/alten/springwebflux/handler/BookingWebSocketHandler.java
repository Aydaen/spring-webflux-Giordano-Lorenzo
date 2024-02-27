package com.alten.springwebflux.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BookingWebSocketHandler implements WebSocketHandler {
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        sessions.add(session);
        return session.receive()
                .then();
    }

    public void sendMessage(String message) {
        for (WebSocketSession session : sessions) {
            session.send(Mono.just(session.textMessage(message)))
                    .subscribe();
        }
    }
}