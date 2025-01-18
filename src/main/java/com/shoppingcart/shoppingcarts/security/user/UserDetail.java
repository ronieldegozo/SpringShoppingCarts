package com.shoppingcart.shoppingcarts.security.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shoppingcart.shoppingcarts.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDetail implements UserDetails{

    private Long id;
    private String email;
    private String password;
    
    private Collection<GrantedAuthority> authorities;


    public static UserDetail createUserDetails (User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    
            return new UserDetail(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities
            );
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    //Account is not expired
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    

    
}
