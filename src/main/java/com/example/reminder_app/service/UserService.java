package com.example.reminder_app.service;

import com.example.reminder_app.dto.UserDTO;
import com.example.reminder_app.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserEntity createNewUser(UserDTO userDTO);


    UserEntity findByEmail(String email);
}
