package org.langchainai.chatmat.config;

import dev.langchain4j.http.client.spring.restclient.SpringRestClientBuilderFactory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.langchainai.chatmat.chat.model.DeepSeekMemory;
import org.langchainai.chatmat.chat.model.DeepSeekService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: LCY
 * @time: 2025-12-02 16:52
 **/
@Configuration
public class DeepSeekConfig {

    @Bean
    ChatModelListener chatModelListener() {
        return new MyChatModelListener();
    }



}
