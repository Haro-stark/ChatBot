package com.example.ChatBot.Service;

import com.example.ChatBot.Model.User;
import com.example.ChatBot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId){
        // the given id might not be present in the database so we put it optional
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    public User createUser(@Validated @RequestBody User user) {
        return (User) userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
