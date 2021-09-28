package com.example.ChatBot.Controller;


import com.example.ChatBot.Model.Chat;
import com.example.ChatBot.Model.User;
import com.example.ChatBot.Repository.UserRepository;
import com.example.ChatBot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final static String uuid = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private boolean authorized(String authToken) throws HttpClientErrorException {
        System.out.println("Inside Auth");
        if(authToken.equals(uuid)) return true;
        else return false;
    }


    @GetMapping("/")
    public List<User> getAllUsers(@RequestHeader("Authorization") String authToken){
        authorized(authToken);
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@RequestHeader("Authorization") String authToken,
                                            @PathVariable(value = "id") Long userId){
        authorized(authToken);
        // the given id might not be present in the database so we put it optional
       return userService.getUserById(userId);

    }

    @PostMapping("/add")
    public User createUser(@RequestHeader("Authorization") String authToken,
                           @Validated @RequestBody User user) {
        authorized(authToken);
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader("Authorization") String authToken,
                       @PathVariable Long id) {
        authorized(authToken);
        userService.deleteUser(id);
    }

    @PostMapping("/update")
    public User updateChat(@RequestHeader("Authorization") String authToken,
                           @Validated @RequestBody User user) {
        authorized(authToken);
        return userService.updateUser(user);
    }

    @GetMapping("/login")
    public ResponseEntity<User> getQuestionById(@RequestHeader("Authorization") String authToken,
                                                @RequestParam("uname") String uname,
                                                @RequestParam("password") String pass ) throws Exception {
        authorized(authToken);
        // the given id might not be present in the database, so we put it optional
        System.out.println(uname+"\n"+pass);
        return userService.getLoginByUsername(uname, pass);
    }

}
