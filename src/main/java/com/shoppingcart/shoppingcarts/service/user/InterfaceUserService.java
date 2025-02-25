package com.shoppingcart.shoppingcarts.service.user;

import com.shoppingcart.shoppingcarts.dto.UserDto;
import com.shoppingcart.shoppingcarts.model.User;
import com.shoppingcart.shoppingcarts.request.*;

public interface InterfaceUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest createUserRequest);
    User updateUser(UpdateUserRequest updateUserRequest, Long userId);
    void deleteUser(Long userId);
    UserDto convertUserToDto(User user);
    UserDto convertCreatedUserToDto(User user);
    User getAuthenticatedUser();
}
