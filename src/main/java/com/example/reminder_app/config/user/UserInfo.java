package com.example.reminder_app.config.user;

import com.example.reminder_app.dto.UserDTO;
import com.example.reminder_app.entity.UserEntity;
import com.example.reminder_app.mapper.UserMapper;
import com.example.reminder_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserInfo  {

    private final UserService userService;



    public UserDTO getCurrentUser() {
        OAuth2User currentUserIdentifier = (OAuth2User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        UserEntity entity =  userService.findByEmail(currentUserIdentifier.getAttribute("email"));
        return UserMapper.INSTANCE.toDto(entity);
    }


}

