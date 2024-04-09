package com.example.foodapp.constants;

public final class FoodAppConstants {
    public static final String URI_V1 = "v1";
    public static final String URI_WELCOME = "/welcome";
    public static final String URI_USER = "/user";
    public static final String URI_MENU = "/menu";
    public static final String URI_CART = "/cart";
    public static final String URI_USER_REGISTER = URI_USER + "/register";
    public static final String URI_USER_GET_ALL = URI_USER + "/list";
    public static final String URI_USER_GET_PARTICULAR_USER = URI_USER + "/individual";
    public static final String URI_USER_UPDATE_PARTICULAR_USER = URI_USER + "/update";
    public static final String URI_USER_DELETE_PARTICULAR_USER = URI_USER + "/delete";
    public static final String URI_MENU_CREATE = URI_MENU + "/add";
    public static final String URI_MENU_GET_ALL = URI_MENU + "/list";
    public static final String URI_MENU_GET_PARTICULAR_ITEM = URI_MENU + "/item";
    public static final String URI_MENU_UPDATE_PARTICULAR_ITEM = URI_MENU + "/update";
    public static final String URI_MENU_DELETE_PARTICULAR_ITEM = URI_MENU + "/delete";
    public static final String URI_CART_ADD = URI_CART + "/add";
    public static final String URI_CART_GET_ITEMS = URI_CART + "/user";
    public static final String URI_CART_UPDATE_PARTICULAR_CART_ITEM = URI_CART + "/update";
    public static final String URI_CART_DELETE_PARTICULAR_ITEM = URI_CART + "/remove";
    public static final String URI_CONFIRM_ORDER = URI_CART + "/confirm";
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9_]+$";
}
