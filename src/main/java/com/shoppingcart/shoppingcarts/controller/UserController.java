package com.shoppingcart.shoppingcarts.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.shoppingcarts.dto.UserDto;
import com.shoppingcart.shoppingcarts.exceptions.AlreadyExistsException;
import com.shoppingcart.shoppingcarts.exceptions.UserNotFoundException;
import com.shoppingcart.shoppingcarts.model.User;
import com.shoppingcart.shoppingcarts.request.CreateUserRequest;
import com.shoppingcart.shoppingcarts.request.UpdateUserRequest;
import com.shoppingcart.shoppingcarts.response.ApiResponse;
import com.shoppingcart.shoppingcarts.service.user.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {

    private final UserService userService;

    
    @GetMapping("/userId")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {

        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return  ResponseEntity.ok(new ApiResponse("User Found!", userDto));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), userId));
        }

    }


    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        
        try {
            User user = userService.createUser(createUserRequest);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.status(CREATED)
                    .body(new ApiResponse("User created successfully!", userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }

    
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest userUpdateRequest, @PathVariable Long userId) {
        try {
            User user = userService.updateUser(userUpdateRequest, userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("User updated successfully!", userDto));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("User deleted successfully!", userId));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


}
