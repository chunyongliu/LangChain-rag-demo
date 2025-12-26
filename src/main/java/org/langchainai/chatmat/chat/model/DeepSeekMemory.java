package org.langchainai.chatmat.chat.model;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

public interface DeepSeekMemory {

    /**
     * 记忆功能
     * @param memoryId
     * @param question
     * @return
     */
    @SystemMessage("你是一个心里专家")
    String chatwithMemory(@MemoryId int memoryId, @UserMessage String question);

    String chat(@MemoryId int memoryId,@UserMessage  String message);

    @SystemMessage("你是一个Langchain4j开发精通者")
    Flux<String> chat(@UserMessage String userMessage, @MemoryId int memoryId);
}
