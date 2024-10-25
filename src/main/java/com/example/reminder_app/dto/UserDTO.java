package com.example.reminder_app.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Data
public class UserDTO implements UserDetails, OAuth2User {

    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @Email
    private String email;



    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public String getName() {
        return String.valueOf(id);
    }

}
