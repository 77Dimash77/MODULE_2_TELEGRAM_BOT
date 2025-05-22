package work.test_api_tg.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import work.test_api_tg.bot.TelegramBotHandler;

@Configuration
@RequiredArgsConstructor
public class TelegramBotConfig {

    private final TelegramBotHandler telegramBotHandler;

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBotHandler);
            System.out.println("✅ Бот Telegram зарегистрирован успешно");
        } catch (TelegramApiException e) {
            System.err.println("❌ Ошибка регистрации Telegram бота: " + e.getMessage());
        }
    }
}