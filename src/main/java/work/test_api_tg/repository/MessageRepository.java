package work.test_api_tg.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import work.test_api_tg.entity.Message;
import work.test_api_tg.entity.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUser(User user);
}