package work.test_api_tg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import work.test_api_tg.config.TelegramBotProperties;

@SpringBootApplication
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TestApiTgApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApiTgApplication.class, args);
    }

}
