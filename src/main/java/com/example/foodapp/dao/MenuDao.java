package com.example.foodapp.dao;

import com.example.foodapp.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDao extends JpaRepository<Menu, Long> {
    boolean existsByItemName(String itemName);
}
