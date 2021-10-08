package com.example.ChatBot.Model.Entity;

import lombok.Data;
import javax.persistence.*;

@Data
// A user model class that will contain all the necessary details of a user to be stored in database.
@Entity
// This model class will automatically be converted to a table in database based on this table annotation
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;

}
