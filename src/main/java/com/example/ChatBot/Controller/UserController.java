package com.example.ChatBot.Controller;


import com.example.ChatBot.Model.Entity.User;
import com.example.ChatBot.Service.UserService;
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
@RequestMapping("/user")
public class UserController {

    private static final Logger LOG = LogManager.getLogger(UserController.class);
    private final UserService userService;

    //an ID that is used to authenticate the request header authentication token
    private final static String uuid = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";

    //Initialized the constructor instead of @autowired annotation
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //This function authorizes the authentication header in the request header
    private void authorized(Optional<String> authToken) throws Exception {
        if (!authToken.isPresent()) throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        if (!authToken.get().equals(uuid)) throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
    }

    //This API gets all the user in database
    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(@RequestHeader("Authorization") Optional<String> authToken) throws Exception {
       try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }

        return userService.getAllUsers();
    }

    // This API gets a user by their ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@RequestHeader("Authorization") Optional<String> authToken,
                                            @PathVariable(value = "id") Long userId) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to  Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        // the given id might not be present in the database so we put it optional
        return userService.getUserById(userId);

    }

    //This API adds a user into the database
    @PostMapping("/addUser")
    public ResponseEntity<User> createUser(@RequestHeader("Authorization") Optional<String> authToken,
                                           @Validated @RequestBody User user) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return userService.addUser(user);
    }

    //This API deletes a user from the database
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Object> delete(@RequestHeader("Authorization") Optional<String> authToken,
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
        return userService.deleteUser(id);
    }

    //This API updates the user from the status
    @PostMapping("/updateUserInfo")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") Optional<String> authToken,
                                           @Validated @RequestBody User user) throws Exception {
        try{
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize: " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return userService.updateUser(user);
    }

    //This API logins the user by comparing password and username
    @GetMapping("/loginUser")
    public ResponseEntity<User> loginUser(@RequestHeader("Authorization") Optional<String> authToken,
                                                @RequestParam("username") String uname,
                                                @RequestParam("password") String pass) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize: " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return userService.getLoginByUsername(uname, pass);
    }

    //This API updates the user from the status
    @PostMapping("/update/addUserChat")
    public ResponseEntity<User> updateUserChat(@RequestHeader("Authorization") Optional<String> authToken,
                                           @Validated @RequestBody User user) throws Exception {
        try{
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to   Authorize: " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return userService.addUserChat(user);
    }

    //This API gets all the user in database
    @GetMapping("/getUserChatsAndCategories")
    public ResponseEntity<Object> getUserChatsAndCategories(@RequestHeader("Authorization") Optional<String> authToken,
                                                            @RequestParam("userId") Long userId) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }
        return userService.getUserChatsAndCategories(userId);
    }


    //This API gets all the user in database
    @GetMapping("/getUsersByRoleStatusAndPermissionStatus")
    public ResponseEntity<List<User>> getAllUsersByStatus(@RequestHeader("Authorization") Optional<String> authToken) throws Exception {
        try {
            authorized(authToken);
        } catch (HttpClientErrorException e) {
            LOG.info("Unable to Authorize : " + e.getMessage());
            if (e.getStatusCode() == HttpStatus.NOT_FOUND)
                return new ResponseEntity("Authorization Key maybe Missing or Wrong", HttpStatus.NOT_FOUND);
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED)
                return new ResponseEntity("Authorization Process Failed", HttpStatus.UNAUTHORIZED);
        }

        return userService.getAllUsersByStatus();
    }

}
