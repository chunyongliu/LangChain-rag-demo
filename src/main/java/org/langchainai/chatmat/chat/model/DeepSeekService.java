package org.langchainai.chatmat.chat.model;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Flux;

/**
 * @author: LCY
 * @time: 2025-12-02 16:37
 **/
@AiService
public interface DeepSeekService {

    /**
     * 单个汇话
     * @param question
     * @return
     */
    @SystemMessage("你是一个计算机专家")
    String chat(String question);

    /**
     * 流式推送
     * @param question
     * @return
     */
    @SystemMessage("你是一个计算机专家")
    Flux<String> chatWithStream(String question);


}
