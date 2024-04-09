package com.example.foodapp.service;

import com.example.foodapp.dao.MenuDao;
import com.example.foodapp.entities.Menu;
import com.example.foodapp.exceptions.InvalidDataException;
import com.example.foodapp.exceptions.ItemAlreadyExistsException;
import com.example.foodapp.exceptions.ItemNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    //Below are the CRUD operations
    //create
    @Override
    public void createItem(Menu menu) throws ItemAlreadyExistsException, InvalidDataException {
        //validate menu parameters
        validateMenu(menu);

        if (menuDao.existsById(menu.getItemId())) {
            throw new ItemAlreadyExistsException(menu.getItemId());
        } else if (menuDao.existsByItemName(menu.getItemName())) {
            throw new ItemAlreadyExistsException(menu.getItemName());
        } else {
            menuDao.save(menu);
        }
    }
    //read all
    @Override
    public List<Menu> getMenu() {
        return menuDao.findAll();
    }
    //read
    public Menu getItemById(long itemId) throws ItemNotFoundException, InvalidDataException {
        //validate itemId
        validateItemId(itemId);
        return menuDao.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }
    //update
    @Override
    public void updateItem(long itemId, String itemName, double itemPrice) throws ItemNotFoundException, InvalidDataException {
        //validate parameters
        if (itemId==0) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_ITEM_ID);
        } else if (!StringUtils.isNotBlank(itemName)) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_EMPTY_ITEM_NAME);
        }

        Menu existingItem = menuDao.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        if (existingItem != null) {
            existingItem.setItemName(itemName);
            existingItem.setItemPrice(itemPrice);
            menuDao.save(existingItem);
        }
    }
    //delete
    @Override
    public void deleteItemById(long itemId) throws ItemNotFoundException {
        if (menuDao.existsById(itemId)) {
            menuDao.deleteById(itemId);
        } else {
            throw new ItemNotFoundException(itemId);
        }
    }

    private void validateItemId(long itemId) throws InvalidDataException {
        if (itemId==0) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_ITEM_ID);
        }
    }

    private void validateMenu(Menu menu) throws InvalidDataException {
        if (!StringUtils.isNotBlank(menu.getItemName())) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_EMPTY_ITEM_NAME);
        } else if (menu.getItemId()==0) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_ITEM_ID);
        } else if (menu.getItemPrice()==0) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_ITEM_PRICE);
        }
    }
}
