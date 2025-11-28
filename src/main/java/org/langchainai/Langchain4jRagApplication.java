package org.langchainai;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Langchain4jRagApplication {

    private static final Logger log = LoggerFactory.getLogger(Langchain4jRagApplication.class);

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        ConfigurableApplicationContext application = SpringApplication.run(Langchain4jRagApplication.class, args);
        Environment env = application.getEnvironment();
        String port = env.getProperty("server.port");
        log.info("Application启动成功，服务端口为：" + port);


    }
}