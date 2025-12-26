package org.langchainai.chatmat.chat;

import dev.langchain4j.service.TokenStream;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.langchainai.chatmat.chat.model.DeepSeekMemory;
import org.langchainai.chatmat.chat.model.DeepSeekService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

/**
 * @description:
 * @author: LCY
 * @time: 2025-12-02 16:38
 **/
@RestController
@RequestMapping("/chatDeepSeek")
public class DeepSeekServiceImpl {

    @Resource
    private DeepSeekService deepSeekService;

    @Resource(name = "DeepSeek01")
    private  DeepSeekMemory deepSeekMemory;

    @GetMapping("/chatByModel")
    public String model( String question) {
        return deepSeekService.chat(question);
    }

    @GetMapping(value = "/chatWithStream",produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatWithStream(String question) {
        return deepSeekService.chatWithStream(question);
    }



    @GetMapping(value = "/chatWithMemory",produces = TEXT_EVENT_STREAM_VALUE)
    public String chatWithMemory(@RequestParam(value = "message", defaultValue = "算法的特性是什么") String message,@RequestParam("userid") Integer userid) {
        return deepSeekMemory.chatwithMemory(userid,message);
    }


    @GetMapping(value = "/chattools",produces = TEXT_EVENT_STREAM_VALUE)
    public String chat(@RequestParam(value = "message", defaultValue = "算法的特性是什么") String message,@RequestParam("userid") Integer userid) {
        return deepSeekMemory.chat(userid,message);
    }


    @RequestMapping(value = "/memory_stream_chat",produces ="text/stream;charset=UTF-8")
    public Flux<String> memoryStreamChat(@RequestParam(defaultValue="我是谁") String message, HttpServletResponse response) {
        TokenStream stream = deepSeekService.stream(message, LocalDate.now().toString());

        return Flux.create(sink -> {
            stream.onPartialResponse(s -> sink.next(s))
                    .onCompleteResponse(c -> sink.complete())
                    .onError(sink::error)
                    .start();

        });
    }




}
