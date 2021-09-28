package com.example.ChatBot.Controller;
import com.example.ChatBot.ExceptionController;
import com.example.ChatBot.Model.Chat;
import com.example.ChatBot.Repository.ChatRepository;
import com.example.ChatBot.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


import javax.persistence.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final static String uuid = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    private boolean authorized(String authToken) throws HttpClientErrorException {
        System.out.println("Inside Auth");
        if(authToken.equals(uuid)) return true;
        else return false;
    }

    @GetMapping("")
    public List<Chat> getAllChats(@RequestHeader("Authorization") String authToken) throws Exception {
        authorized(authToken);
        return chatService.getAllChats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChatById(@RequestHeader("Authorization") String authToken,
                                            @PathVariable(value = "id") Long chatId) throws Exception {
        authorized(authToken);
        // the given id might not be present in the database so we put it optional
        return chatService.getChatById(chatId);
    }

    @PostMapping("/add")
    public Chat createChat(@RequestHeader("Authorization") String authToken,
                           @Validated @RequestBody Chat chat) throws Exception {
        authorized(authToken);
        return (Chat) chatService.createChat(chat);
    }


    @DeleteMapping("/{id}")
    public void delete(@RequestHeader("Authorization") String authToken,
                       @PathVariable Long id) throws Exception {
        authorized(authToken);
        chatService.delete(id);
    }

    @PostMapping("/update")
    public Chat updateChat(@RequestHeader("Authorization") String authToken,
                           @Validated @RequestBody Chat chat) throws Exception {
        authorized(authToken);
        return chatService.updateChat(chat);
    }

    @GetMapping("/question")
        public ResponseEntity<Chat> getQuestionById(@RequestHeader("Authorization") String authToken,
                                                    @RequestParam("question") Long chatId) throws Exception {
        authorized(authToken);
        // the given id might not be present in the database, so we put it optional
        return chatService.getQuestionById(chatId);
    }


}
