package com.example.ChatBot.Model.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
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
    @Column(nullable = true)
    private String createdDate;
    @Column(nullable = true)
    private String updatedDate;
    @Column(nullable = false)
    private boolean status;

    @OneToMany(targetEntity = Chat.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userId")
    private List<Chat> chatList = new ArrayList<Chat>();

    @ManyToMany(targetEntity = Category.class,  cascade=CascadeType.MERGE)
    @JoinTable(
        name="user_categories",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Category> categoryList = new ArrayList<Category>();

    @ManyToMany(targetEntity = Role.class, cascade = CascadeType.MERGE)
    @JoinTable(
            name="user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roleList = new ArrayList<>();

}
