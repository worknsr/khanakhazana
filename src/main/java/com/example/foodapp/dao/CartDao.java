package com.example.foodapp.dao;

import com.example.foodapp.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartDao extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserUsernameAndMenuItemId(String username, long itemId);

    List<Cart> findByUserUsername(String username);
}
