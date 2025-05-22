package work.test_api_tg.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import work.test_api_tg.entity.Message;
import work.test_api_tg.entity.User;
import work.test_api_tg.repository.MessageRepository;
import work.test_api_tg.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TelegramBotHandler extends TelegramLongPollingBot {

    private final UserRepository userRepo;

    private final MessageRepository messageRepository;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String messageText = update.getMessage().getText().trim();
        Long chatId = update.getMessage().getChatId();

        // 1. Проверка: есть ли уже авторизованный пользователь
        Optional<User> existing = userRepo.findByChatId(chatId);
        if (existing.isPresent() && existing.get().isAuthorized()) {
            User user = existing.get();
            String name = user.getName();
            sendMessage(chatId, "🔁 Эхо для пользователя " + name  + ": " + messageText);
            Message message = new Message();
            message.setText(messageText);
            message.setCreatedAt(LocalDateTime.now());
            message.setUser(user);
            messageRepository.save(message);
            return;
        }

        // 2. Попытка авторизации по токену
        Optional<User> userOpt = userRepo.findByTelegramToken(messageText);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setChatId(chatId);
            user.setAuthorized(true);
            userRepo.save(user);
            sendMessage(chatId, "✅ Авторизация прошла успешно, " + user.getName() + "!");
        } else {
            sendMessage(chatId, "❌ Токен не найден. Введите корректный токен для авторизации.");
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId.toString());
        msg.setText(text);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
