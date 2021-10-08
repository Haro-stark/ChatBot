package com.example.ChatBot.Repository;

import com.example.ChatBot.Model.Entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* Repository is an interface that extends JPARepository class. It will provide us with all the main
 * database operational commands
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

//    List<Chat> findChatsByChatId()
}
