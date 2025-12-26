package org.langchainai.chatmat.chat.redistool;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;

/**
 * @description:
 * @author: LCY
 * @time: 2025-12-26 15:14
 **/
@Component
@Slf4j
public class PersistentChatMemoryStore implements ChatMemoryStore {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        Object raw = redisTemplate.opsForValue().get(String.valueOf(memoryId));
        if (raw == null) {
            // todo 从数据库中查询最近的历史消息记录
            return List.of(); // 返回空历史，表示是第一次对话
        }
        return messagesFromJson(raw.toString());
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String json = messagesToJson(messages);
        redisTemplate.opsForValue().set(String.valueOf(memoryId), json);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        redisTemplate.delete(String.valueOf(memoryId));
    }
}