package org.langchainai.chatmat.chat;


import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: LCY
 * @time: 2025-12-02 10:01
 **/
@RestController
@RequestMapping("/chatDeepSeek")
public class chatDeepSeek {

    @Autowired
    private OpenAiChatModel openAiChatModel;



    @GetMapping("/chat")
    public String chat(@RequestParam String question) {

        return openAiChatModel.chat(question);
    }

}
