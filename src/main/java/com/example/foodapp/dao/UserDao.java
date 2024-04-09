package com.example.foodapp.dao;

import com.example.foodapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
}
