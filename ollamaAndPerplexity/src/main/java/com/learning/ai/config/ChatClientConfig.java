package com.learning.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {
    @Bean
    public ChatClient perplexityChatClient(OpenAiChatModel openAiChatModel) {
        return ChatClient.create(openAiChatModel);
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel) {
        ChatClient.Builder chatClientBuilder = ChatClient.builder(ollamaChatModel)
                .defaultSystem("""
                        You are an internal HR assistant. Your role is to help\s
                        employees with questions related to HR policies, such as\s
                        leave policies, working hours, benefits, and code of conduct.
                        If a user asks for help with anything outside of these topics,\s
                        kindly inform them that you can only assist with queries related to\s
                        HR policies.
                        """);
        return chatClientBuilder.build();
    }
}
