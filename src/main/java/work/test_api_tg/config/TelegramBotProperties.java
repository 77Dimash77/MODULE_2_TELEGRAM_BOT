package work.test_api_tg.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramBotProperties {
    private String username;
    private String token;
}