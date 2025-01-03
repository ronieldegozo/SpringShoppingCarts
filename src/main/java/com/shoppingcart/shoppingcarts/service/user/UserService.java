package com.shoppingcart.shoppingcarts.service.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.shoppingcart.shoppingcarts.exceptions.AlreadyExistsException;
import com.shoppingcart.shoppingcarts.exceptions.ResourceNotFoundException;
import com.shoppingcart.shoppingcarts.model.User;
import com.shoppingcart.shoppingcarts.repository.UserRepository;
import com.shoppingcart.shoppingcarts.request.CreateUserRequest;
import com.shoppingcart.shoppingcarts.request.UpdateUserRequest;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService implements InterfaceUserService {

    private final UserRepository userRepository;
    
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User not found")
        );
    }

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        return Optional.of(createUserRequest)
                .filter(user -> !userRepository.existByEmail(user.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());
                    user.setEmail(req.getEmail());
                    user.setPassword(req.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(() -> new AlreadyExistsException(createUserRequest.getEmail() + " User already exist"));
    }

    @Override
    public User updateUser(UpdateUserRequest updateUserRequest, Long userId) {
        
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(updateUserRequest.getFirstName());
            existingUser.setLastName(updateUserRequest.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () ->{
            throw new ResourceNotFoundException("User not found!");
        });
    }
    
}
