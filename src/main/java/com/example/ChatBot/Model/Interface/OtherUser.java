package com.example.ChatBot.Model.Interface;

import lombok.Data;
import java.util.List;

@Data
public class OtherUser {
    private long id; //variable to store the User ID
    private String firstName; //variable to store the User First Name
    private String lastName;//variable to store the User Last Name
    private String email;//variable to store the User email
    private int age;//variable to store the User age
    private String password; //variable to store the User Password
    private List<OtherUserChats> chats;
    private List<OtherUserCategory> categories;
}
