package com.learning.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient perplexityChatClient;
    private final ChatClient ollamaChatClient;

    public ChatController(@Qualifier("perplexityChatClient") ChatClient perplexityChatClient,
                                    @Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
        this.perplexityChatClient = perplexityChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }

    @GetMapping("/perplexity/chat")
    public String perplexityAIChat(@RequestParam("message") String message) {
        return perplexityChatClient.prompt(message).call().content();
    }

    @GetMapping("/ollama/chat")
    public String ollamaChat(@RequestParam("message") String message) {
        return ollamaChatClient.prompt(message).call().content();
    }

}