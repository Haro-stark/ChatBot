package com.example.ChatBot.Model.Interface;

import lombok.Data;

@Data
public class UserChatsDTO {
    private long id;
    private String answer;
    private String answerDate;
    private String question;
    private String questionDate;
    private String updateDate;
}
