package org.langchainai.chatmat.chat.model;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

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
}
