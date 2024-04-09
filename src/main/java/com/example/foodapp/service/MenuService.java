package com.example.foodapp.service;

import com.example.foodapp.entities.Menu;
import com.example.foodapp.exceptions.InvalidDataException;
import com.example.foodapp.exceptions.ItemAlreadyExistsException;
import com.example.foodapp.exceptions.ItemNotFoundException;

import java.util.List;

public interface MenuService {
    public void createItem(Menu menu) throws ItemAlreadyExistsException, InvalidDataException;
    public List<Menu> getMenu();
    public Menu getItemById(long itemId) throws ItemNotFoundException, InvalidDataException;
    public void updateItem(long itemId, String itemName, double itemPrice) throws ItemNotFoundException,
            InvalidDataException;
    public void deleteItemById(long itemId) throws ItemNotFoundException;
}
