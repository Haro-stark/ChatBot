package com.example.ChatBot.Service;

import com.example.ChatBot.DateTime;
import com.example.ChatBot.Model.Chat;
import com.example.ChatBot.Repository.ChatRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Haroon Rasheed
 * @version 1.5
 * @Description This class implements logic of API. The Controller send data to their respective service class.
 * This class is Chat Service class which has show all chats, get chat by certain ID, update chat and delete chat
 * certain ID. Logger is also used to keep tracks of logs whenever any api is called the logs will be saved in
 * file.
 * @creationDate 07 October 2021
 */
@Service
public class ChatService {

    private static final Logger LOG = LogManager.getLogger(ChatService.class);
    private final ChatRepository chatRepository;

    //Initialized the constructor instead of autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    /**
     * @return ResponseEntity which is a list of chats. and in else it just returns not found Http status.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function get and show all the chats which are saved in database. The data from database
     * comes in list so chatlist.
     * @creationDate 07 October 2021
     */
    public ResponseEntity<List<Chat>> getAllChats() {

        try {
            Optional<List<Chat>> chat = Optional.of(chatRepository.findAll());
            if (chat.isPresent()) {
                return ResponseEntity.ok().body(chat.get());
            } else {
                LOG.info("Chats returned as empty in getAllChats() : " + chat);
                return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity("Unable to get all chats!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    /**
     * @return ResponseEntity which is a chat of a specific user. and in else it just returns not found Http status.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function that requests the repository to get chats by id
     * @creationDate 07 October 2021
     */
    public ResponseEntity<Chat> getChatById(Long chatId) {
        try {
            Optional<Chat> chat = chatRepository.findById(chatId);
            if (chat.isPresent()) {
                return ResponseEntity.ok().body(chat.get());
            } else {
                LOG.info("Chat by ID returned as empty in getChatById() : " + chat);
                return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity("Unable to get chat by id!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    /**
     * @return ResponseEntity which is a chat recently added to the database. and in else it just returns not found Http status.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function that requests the repository to add a new chat to database
     * @creationDate 07 October 2021
     */
    public ResponseEntity<Chat> createChat(Chat chat) {
        try {
            String date = DateTime.getDateTime();
            chat.setAnswerDate(date);
            chat.setQuestionDate(date);
            Chat chat1 = chatRepository.save(chat);
            return ResponseEntity.ok().body(chat1);
        } catch (Exception e) {
            LOG.info("Chat by ID returned as empty in getChatById() : " + chat);
            return new ResponseEntity("Unable to Add Chat\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @return ResponseEntity which is an Object. and in else it just returns not found Http status.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function that requests the repository to delete a chat based on id
     * @creationDate 07 October 2021
     */
    public ResponseEntity<Object> delete(Long id) {
        try {
            chatRepository.deleteById(id);
            return new ResponseEntity("User Deleted!\n", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Unable to delete User!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    /**
     * @return ResponseEntity which is a aChat object that has been updated. and in else it just returns not found Http status.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function that updates the repository to update a chat
     * @creationDate 07 October 2021
     */
    public ResponseEntity<Chat> updateChat(Chat chat) {
        if (chat != null) {
            try {
                String date = DateTime.getDateTime();
                chat.setAnswerDate(date);
                chat.setQuestionDate(date);
            } catch (Exception e) {
                return new ResponseEntity("Can not set the date and time of chat!\n"+e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
            }
        }
        try {
            return ResponseEntity.ok().body(chatRepository.save(chat));
        } catch (HttpMessageNotReadableException e) {
            return new ResponseEntity("Please Provide a valid input format to Save a Chat!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update chat\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @return ResponseEntity which is a Chat object. and in else it just returns not found Http status.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This  function that requests the repository to get a question based on its id
     * @creationDate 07 October 2021
     */
    public ResponseEntity<Chat> getQuestionById(Long chatId) {

        try{
            Optional<Chat> chat = chatRepository.findById(chatId);
            if (chat.isPresent()) {
                return ResponseEntity.ok().body(chat.get());
            } else {
                return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity("Unable to get the chat by id!\n"+e.getMessage() , HttpStatus.BAD_REQUEST);
        }


    }


}
