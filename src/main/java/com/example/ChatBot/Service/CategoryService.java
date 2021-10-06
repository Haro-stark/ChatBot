package com.example.ChatBot.Service;
import com.example.ChatBot.Model.Category;
import com.example.ChatBot.Repository.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.converter.HttpMessageNotReadableException;

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

    //Service function that requests the repository to get all categories
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
    public ResponseEntity<List<Category>> addCategory(List<Category> categories) {
        try {
            for(Category category: categories){
                categoryRepository.save(category);
            }
            return ResponseEntity.ok().body(categories);
        }catch (HttpMessageNotReadableException e){
            return new ResponseEntity("Please Provide a valid input format to Save a category!\n"+e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity("Unable to Add Chat\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    //Service function that requests the repository to get all category
    public ResponseEntity<Category> deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity("Category Deleted!\n", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Unable to delete Category!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //Service function that requests the repository to get all category
    public ResponseEntity<Category> updateCategory(Category category) {
        try {
            Category categoryObj = categoryRepository.save(category);
            return ResponseEntity.ok().body(categoryObj);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update Category\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
