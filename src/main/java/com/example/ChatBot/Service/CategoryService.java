package com.example.ChatBot.Service;
import com.example.ChatBot.Model.Entity.Category;
import com.example.ChatBot.Repository.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.List;
import java.util.Optional;

/**
 * @author Haroon Rasheed
 * @version 1.5
 * @description This class implements logic of API. The Controller send data to their respective service class.
 * This class is Category Service class which has the following functions/API's show all categories, get category by certain
 * ID, update category and delete category by certain ID. Logger is also used to keep tracks of logs whenever any api is called
 * the logs will be saved in file.
 * @creationDate 07 October 2021
 */

@Service
public class CategoryService {
    private static final Logger LOG = LogManager.getLogger(ChatService.class);
    private final CategoryRepository categoryRepository;

    //Initialized the constructor instead of autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * @return ResponseEntity which is a category list. and in else it just returns not found Http status.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function retrieves all the Categories which are saved in database. The data from database
     * comes in list so categorylist.
     * @creationDate 07 October 2021
     */
    public ResponseEntity<List<Category>> getAllCategories() {
        try{
            Optional<List<Category>> categories = Optional.of(categoryRepository.findAll());
            if (categories.isPresent()) {
                return ResponseEntity.ok().body(categories.get());
            } else {
                LOG.info("Chats returned as empty in getAllChats() : " + categories);
                return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity("Error retrieving all users!\n"+ e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    /**
     * @return A responseEntity with all the list of categories and an Http Status
     * @author Haroon Rahseed
     * @version 1.5
     * @description Save Category into database by getting values from controller
     * @creationDate 07 October 2021
     */
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

    /**
     * @return ResponseEntity having Http status code a response body with particular message
     * @author Haroon Rasheed
     * @description Deletes category from db
     * @creationDate 07 October 2021
     */
    public ResponseEntity<Object> deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity("Category Deleted!\n", HttpStatus.OK);
        } catch (DataAccessException e){
            return new ResponseEntity("The category you want to delete does not exist!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity("Unable to delete Category!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @return ResponseEntity having Http status code a response body
     * @author Haroon Rasheed
     * @description Updates category from database
     * @creationDate 07 October 2021
     */
    public ResponseEntity<Category> updateCategory(Category category) {
        try {
            Category categoryObj = categoryRepository.save(category);
            return ResponseEntity.ok().body(categoryObj);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update Category\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
