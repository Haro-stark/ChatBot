package com.example.ChatBot.Model;

import javax.persistence.*;

// A user model class that will contain all the necessary details of a user to be stored in database.
@Entity
// This model class will automatically be converted to a table in database based on this table annotation
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
