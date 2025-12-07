package com.learning.rag.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient ollamaChatClient;

    public ChatController(@Qualifier("chatMemoryChatClient") ChatClient ollamaChatClient) {
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/ollama/chat")
    public String ollamaChat(@RequestParam("message") String message) {
        return ollamaChatClient
                .prompt()
                .user(message)
                .call().content();
    }

}