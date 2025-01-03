package com.shoppingcart.shoppingcarts.request;

import lombok.*;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
