package com.shoppingcart.shoppingcarts.data;


import lombok.RequiredArgsConstructor;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.shoppingcart.shoppingcarts.exceptions.AlreadyExistsException;
import com.shoppingcart.shoppingcarts.model.Role;
import com.shoppingcart.shoppingcarts.model.User;
import com.shoppingcart.shoppingcarts.repository.UserRepository;
import com.shoppingcart.shoppingcarts.request.CreateUserRequest;

import jakarta.transaction.Transactional;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        Set<String> roles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultUserIfNotExits();
        createDefaultRoleIfNotExits(roles);
        createDefaultAdminIfNotExits();
    }


    private void createDefaultUserIfNotExits(){

        Role userRole = roleRepository.findByName("ROLE_USER").get();

        for (int i = 1; i<=5; i++){
            String defaultEmail = "user"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user " + i + " created successfully.");
        }
    }


    private void createDefaultAdminIfNotExits(){
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 1; i<=2; i++){
            String defaultEmail = "admin"+i+"@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin user " + i + " created successfully.");
        }
    }


    private void createDefaultRoleIfNotExits(Set<String> roles){
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role:: new).forEach(roleRepository::save);

    }

    public User createUserAccess(CreateUserRequest request, Role role) {
        // Check if the email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("User with email " + request.getEmail() + " already exists.");
        }

        // Create the new user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Use password from API request
        user.setRoles(Set.of(role));

        return userRepository.save(user);
    }
    
    /**
     * Finds a role by its name or creates it if it doesn't exist.
     * @param roleName The name of the role (e.g., "ROLE_ADMIN", "ROLE_USER").
     * @return The Role entity.
     */
    public Role findOrCreateRole(String roleName) {
        // Check if the role already exists
        return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    // If not found, create and save a new role
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return roleRepository.save(newRole);
                });
    }
}