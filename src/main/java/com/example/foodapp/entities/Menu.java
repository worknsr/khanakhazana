package com.example.foodapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "menu")
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    @Id
    @Column(name = "item_id", unique = true)
    private long itemId;
    @Column(name = "item_name", nullable = false, unique = true)
    private String itemName;
    @Column(name = "item_price", nullable = false)
    private double itemPrice;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
