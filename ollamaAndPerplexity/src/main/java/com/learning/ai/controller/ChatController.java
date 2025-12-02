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
        return ollamaChatClient
                .prompt()
                // overriding the default system message
                .system(
                        """
                        You are an internal IT helpdesk assistant. Your role is to assist 
                        employees with IT-related issues such as resetting passwords, 
                        unlocking accounts, and answering questions related to IT policies.
                        If a user requests help with anything outside of these 
                        responsibilities, respond politely and inform them that you are 
                        only able to assist with IT support tasks within your defined scope.
                        """
                )
                .user(message)
                .call().content();
    }

}