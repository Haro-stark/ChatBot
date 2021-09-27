package com.example.ChatBot.Controller;
import com.example.ChatBot.Model.Chat;
import com.example.ChatBot.Repository.ChatRepository;
import com.example.ChatBot.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/chats")
    public List<Chat> getAllChats(){

        return chatService.getAllChats();
    }

    @GetMapping("/chats/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable(value = "id") Long chatId){
        // the given id might not be present in the database so we put it optional
        return chatService.getChatById(chatId);
    }

    @PostMapping("/chats/add")
    public Chat createChat(@Validated @RequestBody Chat chat) {
        return (Chat) chatService.createChat(chat);
    }


    @DeleteMapping("/chats/{id}")
    public void delete(@PathVariable Long id) {
        chatService.delete(id);
    }

}
