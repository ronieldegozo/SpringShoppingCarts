package com.shoppingcart.shoppingcarts.dto;

import java.util.Collection;
import java.util.List;
import com.shoppingcart.shoppingcarts.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    
    private List<OrderDto> orders;
    private CartDto cart;
    private List<String> roles; 
}
