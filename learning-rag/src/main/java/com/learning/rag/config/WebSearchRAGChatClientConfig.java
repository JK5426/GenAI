package com.learning.rag.config;

import com.learning.rag.advisors.TokenUsageAuditAdvisor;
import com.learning.rag.rag.WebSearchDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class WebSearchRAGChatClientConfig {

    @Bean("webSearchRAGChatClient")
    public ChatClient chatClient(OllamaChatModel ollamaChatModel,
                                 ChatMemory chatMemory, RestClient.Builder restClientBuilder) {
        ChatClient.Builder chatClientBuilder = ChatClient.builder(ollamaChatModel);
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        var webSearchRAGAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(WebSearchDocumentRetriever.builder()
                        .restClientBuilder(restClientBuilder).maxResults(5).build())
                .queryTransformers(RewriteQueryTransformer.builder().chatClientBuilder(chatClientBuilder.clone()).targetSearchSystem("Web-Search").build())
                .build();

        return chatClientBuilder
                .defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor, tokenUsageAdvisor,
                        webSearchRAGAdvisor))
                .build();
    }
}