package com.example.ChatBot.Service;


import com.example.ChatBot.DateTime;
import com.example.ChatBot.Model.Chat;
import com.example.ChatBot.Model.User;
import com.example.ChatBot.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
//        long millis = System.currentTimeMillis();
//        java.util.Date date = new java.util.Date(millis);
//        for (int i=0; i< user.getCategoryList().size(); i++)
//        {
//            user.getCategoryList().get(i).addUser(user, true);
//        }

        String date = DateTime.getDateTime();
        int size = user.getChatList().size();
        for(int i=0; i<size; i++)
        {
            user.getChatList().get(i).setAnswerDate(date);
            user.getChatList().get(i).setQuestionDate(date);
        }

        try {
            User userObj = userRepository.save(user);
            return ResponseEntity.ok().body(userObj);
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
     * @param uname
     * @param pass
     * @return
     **/
    public ResponseEntity<User> getLoginByUsername(String uname, String pass) {

        try {
            User user = userRepository.findByUsername(uname);
            if (user.getPassword().equals(pass)) {
                return new ResponseEntity("Login Successful\n" + "uname: " + user.getUsername() + "\npassword: " + user.getPassword(), HttpStatus.OK);
            } else {
                return new ResponseEntity("Wrong Password\n" + "uname:" + user.getUsername() + "pass:" + user.getPassword(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity("Unable to Find User. Wrong Username\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        }


    }

    public ResponseEntity<User> updateUserChat(User user) {

        String date = DateTime.getDateTime();

        Optional<User> updateUser = userRepository.findById(user.getUserId());
        int size = updateUser.get().getChatList().size();
        long[] existingChatsId = new long[size];

        // Storing all the existing chat id's so that we can compare that if a hat id exist then we update it and not add it
        for(int i=0; i<size; i++){
            existingChatsId[i] = updateUser.get().getChatList().get(i).getChatId();
        }

        // to keep the index record which is needed to update the user at particular index
        int index = 0;
        if(updateUser.isPresent() && user.getChatList() != null){
            for (Chat chat: user.getChatList()) {
                chat.setQuestionDate(date);
                chat.setAnswerDate(date);

                if(ifChatExist(existingChatsId, chat.getChatId())){
                    updateUser.get().getChatList().set(index, chat);
                }else
                {
                    updateUser.get().getChatList().add(chat);
                }

                index++;
            }
        }
        user = updateUser.get();
        try {
            User userObj = userRepository.save(user);
            return ResponseEntity.ok().body(userObj);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update User\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
