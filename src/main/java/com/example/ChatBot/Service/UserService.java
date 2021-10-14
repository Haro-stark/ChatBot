package com.example.ChatBot.Service;


import com.example.ChatBot.DateTime;
import com.example.ChatBot.Model.Entity.Permission;
import com.example.ChatBot.Model.Entity.Role;
import com.example.ChatBot.Model.Entity.User;
import com.example.ChatBot.Model.Interface.OtherUserChatsAndCategories;
import com.example.ChatBot.Model.Interface.OtherUser;
import com.example.ChatBot.Model.Interface.OwnChatsAndCategories;
import com.example.ChatBot.Repository.PermissionRepository;
import com.example.ChatBot.Repository.RoleRepository;
import com.example.ChatBot.Repository.UserRepository;
import com.example.ChatBot.Util.SmsUtil;
import com.example.ChatBot.Util.MailUtil;
import lombok.extern.java.Log;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    private final JavaMailSender javaMailSender;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private String date;


    HttpHeaders headers = new HttpHeaders();
    RestTemplate restTemplate = new RestTemplate();
    final String baseUrl = "http://192.168.100.65:8080/users";
    URI uri;


    //Initialized the constructor instead of autowired
    public UserService(PermissionRepository permissionRepository, UserRepository userRepository, RoleRepository roleRepository, JavaMailSender javaMailSender) {
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
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
        try {
            List<User> users = userRepository.findAll();
            if (users.size() > 0) {
                return ResponseEntity.ok().body(users);
            } else {
                return new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
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
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                return ResponseEntity.ok().body(user.get());
            } else {
                return new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity("Unable to get user by id\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
        // generating random 6 digit number for sms and email verification
        Integer n = 100000 + new Random().nextInt(900000);
        String smsToken = n.toString();
        n = 100000 + new Random().nextInt(900000);
        String emailToken = n.toString();

        // setting the email and sms token in user object that is to be saved in database
        user.setEmailToken(emailToken);
        user.setSmsToken(smsToken);

        // setting user created date
        date = DateTime.getDateTime();
        user.setCreatedDate(date);

        // setting user status to false for now
        user.setStatus(false);

        // sending email to user
        MailUtil mail = new MailUtil(javaMailSender);
        String emailMessage = "Following is your verification code:\n" + emailToken;
        mail.sendEmail(user.getEmail(), emailMessage);

        // sending sms to the user
        this.sendSms(user.getContactNum(), smsToken);

        Integer size = user.getChatList().size();
        for (int i = 0; i < size; i++) {
            user.getChatList().get(i).setCreatedDate(date);
        }
        try {
            User userObj = userRepository.save(user);
            return ResponseEntity.ok().body(userObj);
        } catch (HttpMessageNotReadableException e) {
            return new ResponseEntity("Please Provide a valid input format to Save a category!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
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
     * @return ResponseEntity which returns a user after updating in db. and in else it just returns not found status
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This Service function that requests the repository to update a user from the database.
     * @creationDate 07 October 2021
     */
    public ResponseEntity<User> updateUser(User user) {
//        ResponseEntity<User> updateUser = this.getUserById(user.getUserId());

        try {
            user.setUpdatedDate(DateTime.getDateTime());
            User userObj = userRepository.save(user);
            return ResponseEntity.ok().body(userObj);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update User\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @return ResponseEntity which returns a user after finding in db. and in else it just returns not found status
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
        try {
            if (!user.getChatList().isEmpty()) {
                date = DateTime.getDateTime();
                Integer size = user.getChatList().size();
                for (int i = 0; i < size; i++) {
                    user.getChatList().get(i).setCreatedDate(date);
                }
            }

            existingUser.get().getChatList().addAll(user.getChatList());

        } catch (Exception e) {
            return new ResponseEntity("Unable to add a chat in user!\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        try {
            User userObj = userRepository.save(existingUser.get());
            System.out.println(userObj.getUsername());
            return ResponseEntity.ok().body(userObj);
        } catch (Exception e) {
            return new ResponseEntity("Unable to Update User\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * @return ResponseEntity which return a list of user's all categories and chat list. and in else it just returns not found status
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function gets all the categories and chats of a user from its own database or from its own
     * database or  by calling a third party's API.
     * @creationDate 08 October 2021
     */
    public ResponseEntity<Object> getUserChatsAndCategories(long userId) {
        try {
            Optional<User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                OwnChatsAndCategories ownUser = new OwnChatsAndCategories();
                ownUser.setChats(user.get().getChatList());
                ownUser.setCategories(user.get().getCategoryList());

                return ResponseEntity.ok().body(ownUser);
            } else if (!user.isPresent()) {
                uri = new URI(baseUrl + "/" + userId);
                headers.set("Authorization", "40dc498b-e837-4fa9-8e53-c1d51e01af15");
                HttpEntity<OtherUser> requestEntity = new HttpEntity<>(null, headers);

                ResponseEntity<OtherUser> otherUser;
                try {
                    otherUser = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, OtherUser.class);

                    OtherUserChatsAndCategories otherUserChatsAndCategories = new OtherUserChatsAndCategories();
                    otherUserChatsAndCategories.setOtherUserChats(otherUser.getBody().getChats());
                    otherUserChatsAndCategories.setOtherUserCategories(otherUser.getBody().getCategories());

                    return ResponseEntity.ok().body(otherUserChatsAndCategories);
                } catch (HttpClientErrorException ex) {
                    return new ResponseEntity("Missing request headers for other user api" + ex.getMessage(), HttpStatus.NOT_FOUND);
                } catch (RestClientException e) {
                    return new ResponseEntity(" Request failed because of a server error response" + e.getMessage(), HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity("Unable to get user by id\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * @return ResponseEntity which is a List of user. and in else it just returns not found Http status.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function returns the
     * @creationDate 13 October 2021
     */
    public ResponseEntity<List<User>> getAllUsersByStatus() {
        try {
            List<Role> roles = roleRepository.findByStatus(true);
            List<Permission> permissions = permissionRepository.findByStatus(true);
//            List<User> users = userRepository.findAllByRoleList_StatusAndRoleList_PermissionList_Status(true);
//            List<User> users = userRepository.findAllByStatusAndRoleListInAndRoleList_PermissionListIn(true, roles, permissions);
//            List<User> users = userRepository.findAllByStatusAndRoleListIsAndRoleList_PermissionListIs(true, roles, permissions);
            List<User> users = userRepository.findAllByStatusTrueAndRoleList_StatusTrueAndRoleList_PermissionList_StatusTrue();
            if (users.size() > 0) {
                return ResponseEntity.ok().body(users);
            } else {
                return new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity("Unable to Get All Users\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * @return ResponseEntity which is a chat recently sent to the mobile. and in else it just returns not found Http status.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function sends the number to the "to number"
     * @creationDate 13 October 2021
     */
    public ResponseEntity<String> sendSms(String contactNumber, String smsToken) {
        String message = "Your SmS Verification token for user registration is:\n" + smsToken;
        try {
            String toNumber = contactNumber;
            String response = SmsUtil.sendSms(toNumber, message);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return new ResponseEntity("Unable to send sms\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * @return String which is a phone number.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function returns the phone number against a particular user id
     * @creationDate 13 October 2021
     */
    public ResponseEntity<String> verifyUser(Long userId, String smsToken, String emailToken) {

        try {
            Optional<User> user = Optional.ofNullable(userRepository.findByUserIdAndSmsTokenAndEmailToken(userId, smsToken, emailToken));
            if(user.isPresent()){
                user.get().setStatus(true);
                userRepository.save(user.get());
                return ResponseEntity.ok().body("User Verified");
            }else{
                return ResponseEntity.ok().body("User can not be Verified");
            }
        } catch (Exception e) {
            return new ResponseEntity("Unable to verify user\n" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }



    }
}