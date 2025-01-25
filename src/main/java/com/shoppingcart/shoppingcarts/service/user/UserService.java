package com.shoppingcart.shoppingcarts.service.user;

import java.security.Principal;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.proc.SecurityContext;
import com.shoppingcart.shoppingcarts.dto.UserDto;
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
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User not found")
        );
    }

    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        return Optional.of(createUserRequest)
                .filter(user -> !userRepository.existsByEmail(user.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());
                    user.setEmail(req.getEmail());
                    user.setPassword(passwordEncoder.encode(req.getPassword()));
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

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication  = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        String email = ((Principal) authentication).getName();
        return userRepository.findByEmail(email);
    }

    


}
