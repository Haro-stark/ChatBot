package com.example.ChatBot.Model.Interface;

import lombok.Data;
import java.util.List;

@Data
public class OtherUserChatsAndCategories {
    private List<OtherUserChats> otherUserChats;
    private List<OtherUserCategory> otherUserCategories;
}
