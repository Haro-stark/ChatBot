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
    private String createDate; // variable to store the user when question will be created
    private String updateDate; // variable to store the date when user will be edited/updated
    private String phoneNo; // variable to store the date when user will be edited/updated
    private boolean status; //variable to store delete status of the permission
}
