package com.example.ChatBot.Service;


import com.example.ChatBot.DateTime;
import com.example.ChatBot.Model.Entity.Chat;
import com.example.ChatBot.Model.Entity.User;
import com.example.ChatBot.Repository.UserRepository;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Haroon Rasheed
 * @Description This class implements logic of API. The Controller send data to their respective service class.
 * This class is user Service class which has following functions/API's (major one is Login which authorize the user by
 * email and password), show all user, get user by certain ID, update user and delete user by
 * certain ID. Logger is also used to keep tracks of logs whenever any api is called the logs will be saved in file.
 * @creationDate 07 October 2021
 */
@Log
@Service
public class UserService {

    private final UserRepository userRepository;
    private String date;


    //Initialized the constructor instead of autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @return ResponseEntity which return a list of all users in db. and in else it just return not found status
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function get and show all the user which are saved in database. The data from database
     * comes in list so userlist.
     * @creationDate 07 October 2021
     */
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

    /**
     * @return ResponseEntity which return a user of particular id from db. and in else it just return not found status
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This Service function that requests the repository to get a user by its id.
     * @creationDate 07 October 2021
     */
    public ResponseEntity<User> getUserById(Long userId) {
        try{
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                return ResponseEntity.ok().body(user.get());
            } else {
                return new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity("Unable to get user by id\n"+e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @return ResponseEntity which return a user that has just been added in db. and in else it just return not found status
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This Service function that requests the repository to add a user in the database.
     * @creationDate 07 October 2021
     */
    public ResponseEntity<User> addUser(User user) {


        date = DateTime.getDateTime();
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

    /**
     * @return ResponseEntity which return an Http status code afer deleting from db. and in else it just return not found status
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This Service function that requests the repository to delete a user from the database.
     * @creationDate 07 October 2021
     */
    public ResponseEntity<Object> deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity("User Deleted!\n", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Unable to delete User!\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @return ResponseEntity which returns a user after updating in db. and in else it just return not found status
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This Service function that requests the repository to update a user from the database.
     * @creationDate 07 October 2021
     */
    public ResponseEntity<User> updateUser(User user) {
//        ResponseEntity<User> updateUser = this.getUserById(user.getUserId());

        try {
            User userObj = userRepository.save(user);
            return ResponseEntity.ok().body(userObj);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update User\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @return ResponseEntity which returns a user after finding in db. and in else it just return not found status
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This Service function that requests the repository to find a user from the database based on its username
     * and password.
     * @creationDate 07 October 2021
     */
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

    /**
     * @return ResponseEntity of a user which has been added in db. and in else it just return not found status
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This Service function that requests the repository to add a chat of a user in the database based on
     * its id.
     * @creationDate 07 October 2021
     */
    public ResponseEntity<User> addUserChat(User user) {
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        try{
            if(!user.getChatList().isEmpty()){
                date = DateTime.getDateTime();
                Integer size = user.getChatList().size();
                for(int i=0; i<size; i++)
                {
                    user.getChatList().get(i).setAnswerDate(date);
                    user.getChatList().get(i).setQuestionDate(date);
                }
            }

            existingUser.get().getChatList().addAll(user.getChatList());

        }catch (Exception e){
            return new ResponseEntity("Unable to add a chat in user!\n"+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            User userObj = userRepository.save(existingUser.get());
            System.out.println(userObj.getUsername());
            return ResponseEntity.ok().body(userObj);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update User\n"+ e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * @return ResponseEntity which return a list of all users in db. and in else it just return not found status
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function get and show all the user which are saved in database. The data from database
     * comes in list so userlist.
     * @creationDate 07 October 2021
     */
    public ResponseEntity<List<User>> getUserChats() {
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



}
