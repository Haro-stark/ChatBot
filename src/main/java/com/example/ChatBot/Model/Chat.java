package com.example.ChatBot.Model;

import javax.persistence.*;
import java.util.Date;

// A user model class that will contain all the necessary details of a user to be stored in database.
@Entity
// This model class will automatically be converted to a table in database based on this table annotation
@Table(name="chat")
public class Chat {

    // All column of the user to be stored in database
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String question;
    @Column(nullable = false)
    private String answer;
    @Column(nullable = true)
    private Date questionDate;
    @Column(nullable = true)
    private Date answerDate;

    //Setter and getter of all the fields
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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

    public Date getQuestionDate() {
        return questionDate;
    }
    public void setQuestionDate(Date questionDate) {
        this.questionDate = questionDate;
    }

    public Date getAnswerDate() {
        return answerDate;
    }
    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }
}
