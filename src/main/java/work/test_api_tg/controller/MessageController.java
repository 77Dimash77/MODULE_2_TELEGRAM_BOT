package work.test_api_tg.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import work.test_api_tg.dto.MessageRequest;
import work.test_api_tg.entity.Message;
import work.test_api_tg.entity.User;
import work.test_api_tg.repository.MessageRepository;
import work.test_api_tg.repository.UserRepository;
import work.test_api_tg.service.TelegramService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final UserRepository userRepo;
    private final MessageRepository messageRepo;
    private final TelegramService telegramService;

    @PostMapping("/send")
    public String sendMessage(@RequestBody MessageRequest request,
                              @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow();

        // сохраняем сообщение
        Message message = Message.builder()
                .user(user)
                .text(request.getText())
                .createdAt(LocalDateTime.now())
                .build();
        messageRepo.save(message);

        // отправляем в Telegram
        telegramService.sendToTelegram(user, request.getText());

        return "Сообщение отправлено и сохранено.";
    }

    @GetMapping("/history")
    public List<Message> getMyMessages(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow();
        return messageRepo.findByUser(user);
    }
}