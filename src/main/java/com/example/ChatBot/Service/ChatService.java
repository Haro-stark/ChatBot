package com.example.ChatBot.Service;

import com.example.ChatBot.Model.Chat;
import com.example.ChatBot.Model.User;
import com.example.ChatBot.Repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final ChatRepository chatRepository;

    //Initialized the constructor instead of autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    //Service function that requests the repository to get all chats
    public ResponseEntity<List<Chat>> getAllChats() {
        Optional<List<Chat>> chat = Optional.of(chatRepository.findAll());
        if (chat.isPresent()) {
            return ResponseEntity.ok().body(chat.get());
        } else {
            return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
        }
    }

    //Service function that requests the repository to get chats by id
    public ResponseEntity<Chat> getChatById(Long chatId) {

        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isPresent()) {
            return ResponseEntity.ok().body(chat.get());
        } else {
            return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
        }

    }

    //Service function that requests the repository to add a new chat to database
    public ResponseEntity<Chat> createChat(Chat chat) {

        long millis = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(millis);
        chat.setAnswerDate(date);
        chat.setQuestionDate(date);

        try {
            return ResponseEntity.ok().body(chatRepository.save(chat));
        } catch (Exception e) {
            return new ResponseEntity("Unable to Add Chat\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    //Service function that requests the repository to delete a chat based on id
    public ResponseEntity<Object> delete(Long id) {
        try {
            chatRepository.deleteById(id);
            return new ResponseEntity("User Deleted!\n", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Unable to delete User!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    //Service function that requests the repository to update a chat
    public ResponseEntity<Chat> updateChat(Chat chat) {

        long millis = System.currentTimeMillis();
        java.util.Date date = new java.util.Date(millis);
        chat.setAnswerDate(date);
        chat.setQuestionDate(date);
        try {
            return ResponseEntity.ok().body(chatRepository.save(chat));
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update chat\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Service function that requests the repository to get a question based on its id
    public ResponseEntity<Chat> getQuestionById(Long chatId) {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isPresent()) {
            return ResponseEntity.ok().body(chat.get());
        } else {
            return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
        }

    }

}
