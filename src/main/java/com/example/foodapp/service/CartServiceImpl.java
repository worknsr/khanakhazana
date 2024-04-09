package com.example.foodapp.service;

import com.example.foodapp.dao.CartDao;
import com.example.foodapp.dao.MenuDao;
import com.example.foodapp.dao.UserDao;
import com.example.foodapp.entities.Cart;
import com.example.foodapp.entities.Menu;
import com.example.foodapp.entities.User;
import com.example.foodapp.exceptions.InvalidDataException;
import com.example.foodapp.exceptions.ItemNotFoundException;
import com.example.foodapp.exceptions.UserNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private UserDao userDao;

    //CREATE
    @Override
    public void addItemToCart(String username, long itemId, int quantity) throws UserNotFoundException,
            ItemNotFoundException, InvalidDataException {
        //validate parameters
        validateCartParams(username, itemId, quantity);

        User user = userDao.findById(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        Menu menu = menuDao.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        Optional<Cart> existingCartItemOptional = cartDao.findByUserUsernameAndMenuItemId(username, itemId);
        if (existingCartItemOptional.isPresent()) {
            Cart existingCartItem = existingCartItemOptional.get();
            int updatedQuantity = existingCartItem.getQuantity() + quantity;
            existingCartItem.setQuantity(updatedQuantity);
            cartDao.save(existingCartItem);
        } else {
            Cart newCartItem = new Cart(user, menu, quantity);
            cartDao.save(newCartItem);
        }
    }
    //READ
    @Override
    public List<Cart> getCartItemsByUsername(String username) throws InvalidDataException {
        if (!StringUtils.isNotBlank(username)) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_USERNAME);
        }
        return cartDao.findByUserUsername(username);
    }
    //UPDATE
    @Override
    public void updateCartItemQuantity(String username, long itemId, int quantity) throws InvalidDataException {
        //validate parameters
        validateCartParams(username, itemId, quantity);

        Cart cartItem = cartDao.findByUserUsernameAndMenuItemId(username, itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        cartItem.setQuantity(quantity);
        cartDao.save(cartItem);
    }
    //DELETE
    @Override
    public void removeItemFromCart(String username, long itemId) throws ItemNotFoundException {
        Cart cartItem = cartDao.findByUserUsernameAndMenuItemId(username, itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        cartDao.delete(cartItem);
    }
    //CONFIRM ORDER
    @Override
    public double confirmOrder(String username) throws InvalidDataException {
        if (!StringUtils.isNotBlank(username)) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_USERNAME);
        }
        List<Cart> cartItemsByUsername = getCartItemsByUsername(username);
        double total = 0;
        for (Cart cartItem : cartItemsByUsername) {
            long itemId = cartItem.getMenu().getItemId();
            int quantity = cartItem.getQuantity();
            double price = getItemPriceByItemId(itemId);
            double subtotal = quantity * price;
            total += subtotal;
        }
        return total;
    }

    private void validateCartParams(String username, long itemId, int quantity) throws InvalidDataException {
        //validate parameters
        if (!StringUtils.isNotBlank(username)) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_USERNAME);
        } else if (itemId==0) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_ITEM_ID);
        } else if (quantity==0) {
            throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_QUANTITY);
        }
    }

    public double getItemPriceByItemId(long itemId) {
        Menu menu = menuDao.findByItemId(itemId);
        if (menu != null) {
            return menu.getItemPrice();
        } else {
            throw new ItemNotFoundException(itemId);
        }
    }
}
