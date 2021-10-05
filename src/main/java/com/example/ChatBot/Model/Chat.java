package com.example.ChatBot.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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


    public long getChatId() {
        return chatId;
    }
    public void setChatId(long id) {
        this.chatId = id;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionDate() {
        return questionDate;
    }
    public void setQuestionDate(String questionDate) {
        this.questionDate = questionDate;
    }

    public String getAnswerDate() {
        return answerDate;
    }
    public void setAnswerDate(String answerDate) {
        this.answerDate = answerDate;
    }
}
