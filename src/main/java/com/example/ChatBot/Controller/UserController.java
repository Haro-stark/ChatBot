package com.example.ChatBot.Controller;


import com.example.ChatBot.Model.User;
import com.example.ChatBot.Repository.UserRepository;
import com.example.ChatBot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){

        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId){
        // the given id might not be present in the database so we put it optional
       return userService.getUserById(userId);

    }

    @PostMapping("/users/add")
    public User createUser(@Validated @RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Long id) {

        userService.deleteUser(id);
    }

}
