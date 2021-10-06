package com.example.ChatBot.Controller;

import com.example.ChatBot.Model.Category;
import com.example.ChatBot.Model.User;
import com.example.ChatBot.Service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private static final Logger LOG = LogManager.getLogger(UserController.class);
    private final CategoryService categoryService;

    //an ID that is used to authenticate the request header authentication token
    private final static String uuid = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";

    //Initialized the constructor instead of @autowired annotation
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    private void authorized(Optional<String> authToken) throws Exception {
        if (!authToken.isPresent()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        if (!authToken.get().equals(uuid)) throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

    //This API gets all the user in database
    @GetMapping("")
    public ResponseEntity<List<Category>> getAllUsers(@RequestHeader("Authorization") Optional<String> authToken) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return categoryService.getAllCategories();
    }


    //This API adds a user into the database
    @PostMapping("/addCategory")
    public ResponseEntity<List<Category>> createUser(@RequestHeader("Authorization") Optional<String> authToken,
                                           @Validated @RequestBody List<Category> categories) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return categoryService.addCategory(categories);
    }

    //This API deletes a user from the database
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Category> delete(@RequestHeader("Authorization") Optional<String> authToken,
                                         @PathVariable Long id) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize: " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return categoryService.deleteCategory(id);
    }

    //This API updates the user from the status
    @PostMapping("/updateUserInfo")
    public ResponseEntity<Category> updateUser(@RequestHeader("Authorization") Optional<String> authToken,
                                           @Validated @RequestBody Category category) throws Exception {
        try{
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize: " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return categoryService.updateCategory(category);
    }


}
