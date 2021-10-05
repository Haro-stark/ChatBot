package com.example.ChatBot.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// A user model class that will contain all the necessary details of a user to be stored in database.
@Entity
// This model class will automatically be converted to a table in database based on this table annotation
@Table(name = "user")
public class User {

    // All column of the user to be stored in database
    @Column(name = "user_id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @OneToMany(targetEntity = Chat.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId")
    private List<Chat> chatList = new ArrayList<Chat>();
    @ManyToMany(targetEntity = Category.class,  cascade=CascadeType.MERGE)
    @JoinTable(
        name="user_categories",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Category> categoryList = new ArrayList<Category>();


    /*Orphan removal is for aggressive removal such as when
    the relation object that's referring gets null, it removes even in that case*/
    public List<Chat> getChatList() {
        return chatList;
    }
    public void setChatList(List<Chat> chat) {
        this.chatList = chat;
    }

    //Setter and getter of all the fields
    public long getUserId() {
        return userId;
    }
    public void setUserId(long id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }


    public List<Category> getCategoryList() {
        return categoryList;
    }
    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
