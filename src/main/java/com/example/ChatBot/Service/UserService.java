package com.example.ChatBot.Service;


import com.example.ChatBot.DateTime;
import com.example.ChatBot.Model.Chat;
import com.example.ChatBot.Model.User;
import com.example.ChatBot.Repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log
@Service
public class UserService {

    private final UserRepository userRepository;

    //Initialized the constructor instead of autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //Service function that requests the repository to get all users
    public ResponseEntity<List<User>> getAllUsers() {
        try
        {
            List<User> users = userRepository.findAll();
            if (users.size()>0) {
                return ResponseEntity.ok().body(users);
            } else {
                return new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity("Unable to Get All Users\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    //Service function that requests the repository to get all users
    public ResponseEntity<User> getUserById(Long userId) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND);
        }

    }

    //Service function that requests the repository to get all users

    /**
     *
     * @param user
     * @return
     */
    public ResponseEntity<User> addUser(User user) {

        String date = DateTime.getDateTime();
        Integer size = user.getChatList().size();
        for(int i=0; i<size; i++)
        {
            user.getChatList().get(i).setAnswerDate(date);
            user.getChatList().get(i).setQuestionDate(date);
        }

        try {
            User userObj = userRepository.save(user);
            return ResponseEntity.ok().body(userObj);
        } catch (HttpMessageNotReadableException e){
            return new ResponseEntity("Please Provide a valid input format to Save a category!\n"+e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Add User\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    //Service function that requests the repository to get all users
    public ResponseEntity<Object> deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity("User Deleted!\n", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Unable to delete User!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //Service function that requests the repository to get all users
    public ResponseEntity<User> updateUser(User user) {
//        ResponseEntity<User> updateUser = this.getUserById(user.getUserId());

        try {
            User userObj = userRepository.save(user);
            return ResponseEntity.ok().body(userObj);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update User\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Service function that requests the repository to get all users

    /**
     *
     * @param username
     * @param password
     * @return
     **/
    public ResponseEntity<User> getLoginByUsername(String username, String password) {

        try {
            Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
            if (user.isPresent()) {
                return new ResponseEntity("Login Successful\n" + "username: " + user.get().getUsername() + "\npassword: " + user.get().getPassword(), HttpStatus.OK);
            } else {
                return new ResponseEntity("Wrong Username or Password\n", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity("Unable to process the request.\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<User> addUserChat(User user) {

        System.out.println(user.getChatList().get(0).getAnswer());
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if(!user.getChatList().isEmpty()){
            String date = DateTime.getDateTime();
            Integer size = user.getChatList().size();
            for(int i=0; i<size; i++)
            {
                user.getChatList().get(i).setAnswerDate(date);
                user.getChatList().get(i).setQuestionDate(date);
            }
        }

        existingUser.get().getChatList().addAll(user.getChatList());
        try {
            User userObj = userRepository.save(existingUser.get());
            System.out.println(userObj.getUsername());
            return ResponseEntity.ok().body(userObj);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update User\n"+ e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public boolean ifChatExist(long[] arr, long toCheckValue){
        for (long id : arr) {
            if (id == toCheckValue) {
                return true;
            }
        }
        return false;
    }


}
