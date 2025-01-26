package com.shoppingcart.shoppingcarts.request;

import lombok.*;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role; // e.g., ROLE_ADMIN or ROLE_USER
}
