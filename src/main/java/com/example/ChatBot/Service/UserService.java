package com.example.ChatBot.Service;


import com.example.ChatBot.Model.User;
import com.example.ChatBot.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    //Initialized the constructor instead of autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Service function that requests the repository to get all users
    public ResponseEntity<List<User>> getAllUsers() {
        Optional<List<User>> user = Optional.of(userRepository.findAll());
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND);
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
    public ResponseEntity<User> createUser(User user) {
        try {
            return ResponseEntity.ok().body(userRepository.save(user));
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
        try {
            return ResponseEntity.ok().body(userRepository.save(user));
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update User\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Service function that requests the repository to get all users
    public ResponseEntity<User> getLoginByUsername(String uname, String pass) {

        try {
            User user = userRepository.findByUsername(uname);
            if (user.getPassword().equals(pass)) {
                return ResponseEntity.ok().body(user);
            } else {
                return new ResponseEntity("Wrong Password\n" + "uname:" + user.getUsername() + "pass:" + user.getPassword(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity("Unable to Find User. Wrong Username\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }


    }

}
