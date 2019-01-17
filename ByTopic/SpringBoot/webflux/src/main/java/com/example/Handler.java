package com.example;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class Handler {

    private static final class Payload {
        public String message;
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        Payload payload = new Payload();
        payload.message = "Hello, WebFlux!";
        return ServerResponse.ok().body(Mono.just(payload), Payload.class);
    }
}
