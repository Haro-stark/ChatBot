package com.example.ChatBot.Service;
import com.example.ChatBot.DateTime;
import com.example.ChatBot.Model.Category;
import com.example.ChatBot.Model.Chat;
import com.example.ChatBot.Repository.CategoryRepository;
import com.example.ChatBot.Repository.ChatRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private static final Logger LOG = LogManager.getLogger(ChatService.class);
    private final CategoryRepository categoryRepository;

    //Initialized the constructor instead of autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public ResponseEntity<List<Category>> getAllCategories() {
        Optional<List<Category>> categories = Optional.of(categoryRepository.findAll());
        if (categories.isPresent()) {
            return ResponseEntity.ok().body(categories.get());
        } else {
            LOG.info("Chats returned as empty in getAllChats() : " + categories);
            return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
        }
    }

    //Service function that requests the repository to add a new chat to database
    public ResponseEntity<Category> addCategory(Category category) {
        try {
            return ResponseEntity.ok().body(categoryRepository.save(category));
        } catch (Exception e) {
            LOG.info("Chat by ID returned as empty in getChatById() : " + category);
            return new ResponseEntity("Unable to Add Chat\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
