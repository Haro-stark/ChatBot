package com.example.ChatBot.Repository;

import com.example.ChatBot.Model.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByStatus(boolean status);
}
