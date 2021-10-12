package com.example.ChatBot.Model.Entity;

import com.example.ChatBot.Model.Entity.Permission;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table (name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    @Column(nullable = true)
    private String createdDate;
    @Column(nullable = true)
    private String updatedDate;
    @Column(nullable = false)
    private boolean status;

    @ManyToMany(cascade = CascadeType.MERGE, targetEntity = Permission.class)
    private List<Permission> permissionList = new ArrayList<>();

}