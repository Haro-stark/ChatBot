package com.example.ChatBot.Service;

import com.example.ChatBot.Model.Chat;
import com.example.ChatBot.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;


    public List<Chat> getAllChats(){

        return chatRepository.findAll();
    }

    public ResponseEntity<Chat> getChatById(@PathVariable(value = "id") Long chatId){
        // the given id might not be present in the database so we put it optional
        Optional<Chat> chat = chatRepository.findById(chatId);
        if(chat.isPresent()) {
            return ResponseEntity.ok().body(chat.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    public Chat createChat(@Validated @RequestBody Chat chat) {
        if(chat.getAnswerDate() == null) {
            long millis=System.currentTimeMillis();
            java.util.Date date=new java.util.Date(millis);
            chat.setAnswerDate(date);
        }
        if(chat.getQuestionDate() == null) {
            long millis=System.currentTimeMillis();
            java.util.Date date=new java.util.Date(millis);
            chat.setQuestionDate(date);
        }
        return (Chat) chatRepository.save(chat);
    }

    @DeleteMapping("/chats/{id}")
    public void delete(@PathVariable Long id) {
        chatRepository.deleteById(id);
    }

}
