package org.langchainai.chatmat.redisembedding;

import dev.langchain4j.community.model.dashscope.QwenEmbeddingModel;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 新增redis的向量
 * @author: LCY
 * @time: 2026-01-05 10:44
 **/
@RestController
@RequestMapping("/api/v1/chat")
public class RedisEmbeddingController {


    @Autowired
    private QwenEmbeddingModel embeddingModel;

    @Autowired
    private EmbeddingStore<TextSegment> embeddingStore;


    @RequestMapping("/addEmbedding")
    public String addEmbedding() {
     //   InMemoryEmbeddingStore<TextSegment> inMemoryEmbeddingStore = new InMemoryEmbeddingStore<>();

        //模型向量化
        TextSegment segment1 = TextSegment.from("""
                预订航班:
                - 通过我们的网站或移动应用程序预订。
                - 预订时需要全额付款。
                - 确保个人信息（姓名、ID 等）的准确性，因为更正可能会产生 25 的费用。
                """);
        Embedding embedding1 = embeddingModel.embed(segment1).content();
      //  inMemoryEmbeddingStore.add(embedding1, segment1);
        // 利用向量模型进行向量化， 然后存储向量到向量数据库
        TextSegment segment2 = TextSegment.from("""
                取消预订:
                - 最晚在航班起飞前 48 小时取消。
                - 取消费用：经济舱 75 美元，豪华经济舱 50 美元，商务舱 25 美元。
                - 退款将在 7 个工作日内处理。
                """);
        Embedding embedding2 = embeddingModel.embed(segment2).content();

     //   inMemoryEmbeddingStore.add(embedding2, segment2);
        embeddingStore.add(embedding1, segment1);
        embeddingStore.add(embedding2, segment2);

        System.out.println("数据已通过Ingestor成功存入Redis。");


        return "addEmbeddingsuccess";
    }


    @PostMapping("/searchEmbedding")
    public ResponseEntity<String> searchEmbedding(String question) {

        // 去向量数据库查询
        // 构建查询条件
        Embedding queryEmbedding = embeddingModel.embed(question).content();
        EmbeddingSearchRequest build = EmbeddingSearchRequest.builder()
                .queryEmbedding(queryEmbedding)

                .maxResults(2)
                .build();

        // 查询
        EmbeddingSearchResult<TextSegment> segmentEmbeddingSearchResult = embeddingStore.search(build);
        // 添加调试信息
        System.out.println("查询问题: " + question);
        System.out.println("匹配数量: " + segmentEmbeddingSearchResult.matches().size());

        segmentEmbeddingSearchResult.matches().forEach(embeddingMatch -> {
            System.out.println("相似度分数: " + embeddingMatch.score());
            System.out.println("匹配文本: " + embeddingMatch.embedded().text());
        });



        segmentEmbeddingSearchResult.matches().forEach(embeddingMatch -> {
            System.out.println(embeddingMatch.score()); // 0.8144288515898701
            System.out.println(embeddingMatch.embedded().text()); // I like football.
        });
        List<String> results = segmentEmbeddingSearchResult.matches().stream()
                .map(match -> match.embedded().text())
                .collect(Collectors.toList());

        String responseText = String.join("\n\n", results);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(responseText);

    }

}
