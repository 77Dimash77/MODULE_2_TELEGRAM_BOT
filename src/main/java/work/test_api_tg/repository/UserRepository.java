package work.test_api_tg.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import work.test_api_tg.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByTelegramToken(String token);
    Optional<User> findByChatId(Long chatId);
}
