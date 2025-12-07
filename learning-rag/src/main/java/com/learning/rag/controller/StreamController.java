package com.learning.rag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:5173")
public class StreamController {

    private final ChatClient ollamaChatClient;

    public StreamController(@Qualifier("chatMemoryChatClient") ChatClient ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping(path = "/stream")
    public Flux<String> stream(@RequestParam("message") String message) {
        return ollamaChatClient.prompt().user(message).stream().content();
    }
}