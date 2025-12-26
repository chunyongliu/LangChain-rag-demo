package org.langchainai.chatmat.chat;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.langchainai.chatmat.chat.model.DeepSeekMemory;
import org.langchainai.chatmat.chat.model.ToolsService;
import org.langchainai.chatmat.chat.redistool.PersistentChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

/**
 * @description:
 * @author: LCY
 * @time: 2025-12-26 15:28
 **/
@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {


    @Autowired
    private OpenAiStreamingChatModel openAiChatModel;


    @Resource
    private PersistentChatMemoryStore chatMemoryStore;

    @Resource
    private  ToolsService assistantTools;

    @GetMapping(value = "/chat", produces ="text/stream;charset=UTF-8")
    public Flux<String> model(@RequestParam(value = "message", defaultValue = "Hello") String message,
                              @RequestParam(value = "memoryId") int memoryId) {

        DeepSeekMemory assistant = getAssistant(memoryId);
        return assistant.chat(message, memoryId);
    }



    private DeepSeekMemory getAssistant(int memoryId) {
        ChatMemoryProvider chatMemoryProvider = o -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(chatMemoryStore)
                .build();

        return AiServices.builder(DeepSeekMemory.class)
                .streamingChatModel(openAiChatModel)
                .chatMemoryProvider(chatMemoryProvider)
                .tools(assistantTools)
                .build();
    }
}
