package com.example.ChatBot.Model.Interface;

import com.example.ChatBot.Model.Entity.Category;
import com.example.ChatBot.Model.Entity.Chat;
import lombok.Data;

import java.util.List;

@Data
public class OwnChatsAndCategories {
    private List<Chat> chats;
    private List<Category> categories;
}