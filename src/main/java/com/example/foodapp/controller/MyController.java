package com.example.foodapp.controller;

import com.example.foodapp.constants.FoodAppConstants;
import com.example.foodapp.entities.Cart;
import com.example.foodapp.entities.Menu;
import com.example.foodapp.entities.User;
import com.example.foodapp.exceptions.*;
import com.example.foodapp.service.CartService;
import com.example.foodapp.service.MenuService;
import com.example.foodapp.service.UserService;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping(FoodAppConstants.URI_V1)
public class MyController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping(FoodAppConstants.URI_WELCOME)
    public String welcome() {
        return "Welcome to KhanaKhazana!";
    }

    // USER RELATED APIs
    //register(create) new user
    @PostMapping(FoodAppConstants.URI_USER_REGISTER)
    public ResponseEntity<String> registerUser(
            @Valid
            @ApiParam(name = "register", value = "App Registration", required = true)
            @RequestBody final User user)
            throws UsernameAlreadyExistException, InvalidDataException{
        try {
            this.userService.createUser(user);
            return ResponseEntity.ok("User added successfully!");
        } catch (UsernameAlreadyExistException e) {
            return ResponseEntity.status(UsernameAlreadyExistException.STATUS_CODE).body(e.getMessage());
        } catch (InvalidDataException e) {
            return ResponseEntity.status(InvalidDataException.STATUS_CODE).body(e.getMessage());
        }
    }
    //read all users
    @GetMapping(FoodAppConstants.URI_USER_GET_ALL)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = this.userService.getAllUsers();
        return ResponseEntity.ok(userList);
    }
    //read user by username
    @GetMapping(FoodAppConstants.URI_USER_GET_PARTICULAR_USER)
    public ResponseEntity<String> getUserByUsername(
            @Valid
            @QueryParam("username") final String username) {
        try {
            this.userService.getUserByUsername(username);
            return ResponseEntity.ok("User with username \""+username+"\" exists");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(UserNotFoundException.STATUS_CODE).body(e.getMessage());
        }  catch (InvalidDataException e) {
            return ResponseEntity.status(InvalidDataException.STATUS_CODE).body(e.getMessage());
        }
    }
    //update existing user
    @PutMapping(FoodAppConstants.URI_USER_UPDATE_PARTICULAR_USER)
    public ResponseEntity<String> updateUserByUsername (
            @QueryParam("username") final String username,
            @QueryParam("password") final String password,
            @QueryParam("user roles") final String userRoles)
            throws UserNotFoundException, InvalidDataException {
        try {
            userService.updateUser(username, password, userRoles);
            return ResponseEntity.ok("User with username \""+username+"\" updated successfully");
        }  catch (UserNotFoundException e) {
            return ResponseEntity.status(UserNotFoundException.STATUS_CODE).body(e.getMessage());
        }  catch (InvalidDataException e) {
            return ResponseEntity.status(InvalidDataException.STATUS_CODE).body(e.getMessage());
        }
    }
    //delete user
    @DeleteMapping(FoodAppConstants.URI_USER_DELETE_PARTICULAR_USER)
    public ResponseEntity<String> deleteUserByUsername(
            @QueryParam("username") final String username)
            throws UserNotFoundException, InvalidDataException {
        try {
            this.userService.deleteUserByUsername(username);
            return ResponseEntity.ok("User "+username+" deleted successfully!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(UserNotFoundException.STATUS_CODE).body(e.getMessage());
        }  catch (InvalidDataException e) {
            return ResponseEntity.status(InvalidDataException.STATUS_CODE).body(e.getMessage());
        }
    }

    // MENU RELATED APIs
    //create food item in menu
    @PostMapping(FoodAppConstants.URI_MENU_CREATE)
    public ResponseEntity<String> addFoodItem(
            @RequestBody final Menu menu)
            throws ItemAlreadyExistsException, InvalidDataException {
        try {
            this.menuService.createItem(menu);
            return ResponseEntity.ok("Item with itemId \""+menu.getItemId()+"\" and itemName \""
                    +menu.getItemName()+"\" having price \""+menu.getItemPrice()+"\" added successfully");
        } catch (InvalidDataException e) {
            return ResponseEntity.status(InvalidDataException.STATUS_CODE).body(e.getMessage());
        } catch (ItemAlreadyExistsException e) {
            return ResponseEntity.status(ItemAlreadyExistsException.STATUS_CODE).body(e.getMessage());
        }
    }
    //get food menu
    @GetMapping(FoodAppConstants.URI_MENU_GET_ALL)
    public ResponseEntity<List<Menu>> getMenu() {
        List<Menu> menuItems = this.menuService.getMenu();
        return ResponseEntity.ok(menuItems);
    }
    //read a particular item from menu
    @GetMapping(FoodAppConstants.URI_MENU_GET_PARTICULAR_ITEM)
    public Menu getItemById(
            @QueryParam("itemId") final long itemId)
            throws ItemNotFoundException, InvalidDataException {
        return this.menuService.getItemById(itemId);
    }
    //update pre-existing item
    @PutMapping(FoodAppConstants.URI_MENU_UPDATE_PARTICULAR_ITEM)
    public ResponseEntity<String> editParticularItem (
            @QueryParam("itemId") final long itemId,
            @QueryParam("item Name") final String itemName,
            @QueryParam("itemPrice") String itemPriceString)
            throws ItemNotFoundException, InvalidDataException {
        try {
            if (!StringUtils.isNotBlank(itemPriceString)) {
                throw new InvalidDataException(InvalidDataException.MESSAGE_INVALID_ITEM_PRICE);
            }
            double itemPrice = Double.parseDouble(itemPriceString);
            menuService.updateItem(itemId, itemName, itemPrice);
            return ResponseEntity.ok("Menu Item updated successfully!");
        } catch (InvalidDataException e) {
            return ResponseEntity.status(InvalidDataException.STATUS_CODE).body(e.getMessage());
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(ItemNotFoundException.STATUS_CODE).body(e.getMessage());
        }
    }
    //delete particular food item
    @DeleteMapping(FoodAppConstants.URI_MENU_DELETE_PARTICULAR_ITEM)
    public ResponseEntity<String> deleteParticularItem(
            @QueryParam("itemId") final long itemId)
            throws ItemNotFoundException {
        try {
            this.menuService.deleteItemById(itemId);
            return ResponseEntity.ok("Menu Item with itemId \"" + itemId+ "\" removed successfully!");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(ItemNotFoundException.STATUS_CODE).body(e.getMessage());
        }
    }

    // CART RELATED APIs
    //add item into cart
    @PostMapping(FoodAppConstants.URI_CART_ADD)
    public ResponseEntity<String> addItemToCart(
            @QueryParam("username") final String username,
            @QueryParam("itemId") final long itemId,
            @QueryParam("quantity") final int quantity)
            throws UserNotFoundException, ItemNotFoundException, InvalidDataException {
        try {
            this.cartService.addItemToCart(username, itemId, quantity);
            return ResponseEntity.ok("Item added to cart successfully");
        } catch (InvalidDataException e) {
            return ResponseEntity.status(InvalidDataException.STATUS_CODE).body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(UserNotFoundException.STATUS_CODE).body(e.getMessage());
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(ItemNotFoundException.STATUS_CODE).body(e.getMessage());
        }
    }
    //read cart items
    @GetMapping(FoodAppConstants.URI_CART_GET_ITEMS)
    public ResponseEntity<?> getCartItems(
            @QueryParam("username") final String username)
            throws InvalidDataException {
        try {
            List<Cart> cartItems = cartService.getCartItemsByUsername(username);
            return ResponseEntity.ok(cartItems);
        } catch (InvalidDataException e) {
            return ResponseEntity.status(InvalidDataException.STATUS_CODE).body(e.getMessage());
        }
    }
    //update cart item
    @PutMapping(FoodAppConstants.URI_CART_UPDATE_PARTICULAR_CART_ITEM)
    public ResponseEntity<String> updateCartItemQuantity(
            @QueryParam("username") final String username,
            @QueryParam("itemId") final long itemId,
            @QueryParam("quantity") final int quantity)
            throws InvalidDataException {
        try {
            cartService.updateCartItemQuantity(username, itemId, quantity);
            return ResponseEntity.ok("Cart item quantity updated successfully");
        } catch (InvalidDataException e) {
            return ResponseEntity.status(InvalidDataException.STATUS_CODE).body(e.getMessage());
        }
    }
    //deleted
    @DeleteMapping(FoodAppConstants.URI_CART_DELETE_PARTICULAR_ITEM)
    public ResponseEntity<String> removeItemFromCart(
            @QueryParam("username") String username,
            @QueryParam("itemId") long itemId)
            throws ItemNotFoundException {
        try {
            cartService.removeItemFromCart(username, itemId);
            return ResponseEntity.ok("Item having itemId \""+itemId+"\" removed from cart successfully");
        } catch (ItemNotFoundException e) {
            return ResponseEntity.status(ItemNotFoundException.STATUS_CODE).body(e.getMessage());
        }
    }
}
