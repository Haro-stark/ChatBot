package com.example.ChatBot.Repository;

import com.example.ChatBot.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>  {
    User findByUsername(@Param(value = "username") String username);
}
