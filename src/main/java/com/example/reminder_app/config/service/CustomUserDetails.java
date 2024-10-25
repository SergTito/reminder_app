package com.example.reminder_app.config.service;


import com.example.reminder_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CustomUserDetails {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetails loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        passwordEncoder.encode(user.getPassword()),
                        Collections.emptySet()
                )).orElseThrow(()->new UsernameNotFoundException("failed to retrieve user: " + email));
    }

}
