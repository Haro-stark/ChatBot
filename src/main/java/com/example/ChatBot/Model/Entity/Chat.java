package com.example.ChatBot.Model.Entity;

import lombok.Data;
import javax.persistence.*;


/**
 * Author:Haroon Rasheed
 * Date:
 */

@Data
// A user model class that will contain all the necessary details of a user to be stored in database.
@Entity
// This model class will automatically be converted to a table in database based on this table annotation
@Table(name="chat")
public class Chat {

    // All column of the user to be stored in database
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private long chatId;
    @Column(nullable = false)
    private String question;
    @Column(nullable = false)
    private String answer;
    @Column(nullable = true)
    private String questionDate;
    @Column(nullable = true)
    private String answerDate;

}
