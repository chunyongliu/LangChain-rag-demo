package org.langchainai.chatmat.chat.model;

import dev.langchain4j.service.*;
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

    /**
     * ●  @SystemMessage 系统消息， 一般做一些预设角色的提示词，设置大模型的基本职责
     * ● 可以通过{{current_date}} 传入参数，  因为预设词中的文本可能需要实时变化
     * ● @V("current_date")， 通过@V传入{{}}中的参数
     * ● 一旦参数不止一个， 就需要通过@UserMessage设置用户信息
     * @param message
     * @return
     */

    TokenStream stream(String message);

    @SystemMessage("""
                您是“Tuling”航空公司的客户聊天支持代理。请以友好、乐于助人且愉快的方式来回复。
                        您正在通过在线聊天系统与客户互动。 
                        在提供有关预订或取消预订的信息之前，您必须始终从用户处获取以下信息：预订号、客户姓名。
                        请讲中文。
					   今天的日期是 {{current_date}}.
                """)
    TokenStream stream(@UserMessage String message,@V("current_date") String currentDate);


}
