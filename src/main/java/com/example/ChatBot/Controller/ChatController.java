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
    //an ID that is used to authenticate the request header authentication token
    private final static String uuid = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";

    //Initialized the constructor instead of @autowired annotation
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    //This function authorizes the authentication header in the request header
    private void authorized(Optional<String> authToken) throws Exception {
        if(!authToken.isPresent()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        if(!authToken.get().equals(uuid)) throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

    //This API retrieves all the chats
    @GetMapping("")
    public ResponseEntity<List<Chat>> getAllChats(@RequestHeader("Authorization") Optional<String> authToken) throws Exception {
        try{
            authorized(authToken);
        }catch(HttpClientErrorException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED) return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return chatService.getAllChats();
    }

    //This API only show certain object by taking on ID number
    @GetMapping("/{id}")
    public ResponseEntity<Chat> getChatById(@RequestHeader("Authorization") Optional<String> authToken,
                                            @PathVariable(value = "id") Long chatId) throws Exception {
        try{
            authorized(authToken);
        }catch(HttpClientErrorException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED) return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return chatService.getChatById(chatId);
    }

    //This API just add the chat
    @PostMapping("/add")
    public ResponseEntity<Chat> createChat(@RequestHeader("Authorization") Optional<String> authToken,
                           @Validated @RequestBody Chat chat) throws Exception {
        try{
            authorized(authToken);
        }catch(HttpClientErrorException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED) return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return chatService.createChat(chat);
    }

    //This API deletes certain chat
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@RequestHeader("Authorization") Optional<String> authToken,
                       @PathVariable Long id) throws Exception {
        try{
            authorized(authToken);
        }catch(HttpClientErrorException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED) return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return chatService.delete(id);
    }

    //This API updates the chat by just giving certain ID all values should be update otherwise other fields will be NULL
    @PostMapping("/update")
    public ResponseEntity<Chat> updateChat(@RequestHeader("Authorization") Optional<String> authToken,
                           @Validated @RequestBody Chat chat) throws Exception {
        try{
            authorized(authToken);
        }catch(HttpClientErrorException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED) return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return chatService.updateChat(chat);
    }

    //This API takes question with ID in header
    @GetMapping("/question")
        public ResponseEntity<Chat> getQuestionById(@RequestHeader("Authorization") Optional<String> authToken,
                                                    @RequestParam("question") Long chatId) throws Exception {
        try{
            authorized(authToken);
        }catch(HttpClientErrorException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if(e.getStatusCode() == HttpStatus.UNAUTHORIZED) return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return chatService.getQuestionById(chatId);
    }

}
