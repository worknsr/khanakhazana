package com.example.foodapp.service;

import com.example.foodapp.entities.Cart;
import com.example.foodapp.exceptions.InvalidDataException;
import com.example.foodapp.exceptions.ItemNotFoundException;
import com.example.foodapp.exceptions.UserNotFoundException;

import java.util.List;

public interface CartService {
    void addItemToCart(String username, long itemId, int quantity) throws UserNotFoundException,
            ItemNotFoundException;
    List<Cart> getCartItemsByUsername(String username) throws InvalidDataException;
    void updateCartItemQuantity(String username, long itemId, int quantity) throws InvalidDataException ;
    void removeItemFromCart(String username, long itemId) throws ItemNotFoundException;
    double confirmOrder(String username) throws InvalidDataException;
}

