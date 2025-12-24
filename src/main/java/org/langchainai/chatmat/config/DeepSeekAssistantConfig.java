package org.langchainai.chatmat.config;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.langchainai.chatmat.chat.model.DeepSeekMemory;
import org.langchainai.chatmat.chat.model.ToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: LCY
 * @time: 2025-12-02 17:46
 **/
@Configuration
public class DeepSeekAssistantConfig {

    @Autowired
    private OpenAiChatModel model;

    @Bean(name = "DeepSeek01")
    public DeepSeekMemory DeepSeek_Assistant(ToolsService toolsService) {
//        ChatModel model = OpenAiChatModel.builder()
//                .baseUrl("https://api.deepseek.com/v1")
//                .apiKey("sk-6a2b7eb8d0da46d5ac20bab7daf4d8ca")
//                .modelName("deepseek-reasoner")
//                .build();

        return AiServices.builder(DeepSeekMemory.class).
                chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .chatModel(model).tools(toolsService)
                .build();

    }
}
