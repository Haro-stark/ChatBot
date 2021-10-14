package com.example.ChatBot.Model.Interface;

import lombok.Data;

@Data
public class SMS {

    private long userId;
    private String userMessage;
    private String fromNumber;


}
