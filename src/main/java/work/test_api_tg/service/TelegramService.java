package work.test_api_tg.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import work.test_api_tg.bot.TelegramBotHandler;
import work.test_api_tg.entity.User;

@Service
@RequiredArgsConstructor
public class TelegramService {

    private final TelegramBotHandler bot;

    public void sendToTelegram(User user, String text) {
        String token = user.getTelegramToken();
        if (token == null) return;

        String messageText = String.format("%s, я получил от тебя сообщение:\n%s", user.getName(), text);
        SendMessage msg = new SendMessage(token, messageText);

        try {
            bot.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}