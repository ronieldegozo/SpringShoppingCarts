package com.shoppingcart.shoppingcarts.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Date;
import com.shoppingcart.shoppingcarts.security.user.UserDetail;

@Component
public class JwtUtils {

    @Value("${auth.token.jwtSecret}")
    private String jwtSecret; 
    
    @Value("${auth.token.expirationInMs}")
    private int expirationMs;

    public String generateTokenForUser(Authentication authentication) {
        UserDetail userPrincipal = (UserDetail) authentication.getPrincipal();

        List<String> roles = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .claim("id", userPrincipal.getId())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() +expirationMs))
                .signWith(key(), SignatureAlgorithm.HS256).compact();

    }
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                 .setSigningKey(key())
                 .build()
                 .parseClaimsJws(token)
                 .getBody().getSubject();
     }

 
     public boolean validateJwtToken(String authToken) {
         try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException| IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }
    }



    
}