package com.langchainai;




import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: LCY
 * @time: 2025-12-01 16:40
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeepSeekTest {


    @Test
    public void testSpringBoot() {
        OpenAiChatModel openAiChatModel = OpenAiChatModel.builder()
                .apiKey("sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                .build();
        // 向模型提问
        String answer = openAiChatModel.chat("你是谁？");
        // 输出结果
        System.out.println(answer);
    }

    @Test
    public void printHello() {

        System.out.println("Hello, World!");
    }



}
