package org.langchainai.chatmat.chat.model;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

/**
 * @author: LCY
 * @time: 2025-12-19 17:06
 **/
@Service
public class ToolsService {

    @Tool("南京有多少个名字的")
    public Integer changeNameCount(@P("姓名") String name) {
        System.out.println(name);
        return 12;
    }
}
