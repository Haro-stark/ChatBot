package com.example.ChatBot.Repository;

import com.example.ChatBot.Model.Entity.Permission;
import com.example.ChatBot.Model.Entity.Role;
import com.example.ChatBot.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/* Repository is an interface that extends JPARepository class. It will provide us with all the main
 * database operational commands
 */
@Repository
public interface UserRepository  extends JpaRepository<User, Long>  {


    /* This function has been made so that our repository can find on the bases of a parameter specified by
     * us in the @param tag
     */
    User findByUsername( String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

//    List<User> findAllByRoleList_StatusAndRoleList_PermissionList_Status(boolean status);
//    List<User> findAllByStatusAndRoleListInAndRoleList_PermissionListIn(boolean status, List<Role> roles, List<Permission> permissions);
//    List<User> findAllByStatusAndRoleListIsAndRoleList_PermissionListIs(boolean status, List<Role> roles, List<Permission> permissions);
    List<User> findAllByStatusTrueAndRoleList_StatusTrueAndRoleList_PermissionList_StatusTrue();
    User findByUserIdAndSmsTokenAndEmailToken(long id, String smsToken, String emailToken);
}
