package com.example.foodapp.entities;

import com.example.foodapp.constants.FoodAppConstants;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "username", nullable = false, unique = true)
    @Size(min = 1, max = 15, message = "Invalid parameter. Username length should be between 1 to 50")
    @Pattern(regexp = FoodAppConstants.USERNAME_PATTERN, message = "Username can only be alphanumeric")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_roles", nullable = false)
    private String userRoles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userRoles='" + userRoles + '\'' +
                '}';
    }
}
