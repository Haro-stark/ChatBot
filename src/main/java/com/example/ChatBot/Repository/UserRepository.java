package com.example.ChatBot.Repository;

import com.example.ChatBot.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/* Repository is an interface that extends JPARepository class. It will provide us with all the main
 * database operational commands
 */
@Repository
public interface UserRepository  extends JpaRepository<User, Long>  {

    /* This function has been made so that our repository can find on the bases of a parameter specified by
     * us in the @param tag
     */
    User findByUsername(@Param(value = "username") String username);
}
