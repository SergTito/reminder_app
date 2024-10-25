package com.example.reminder_app.controller;


import com.example.reminder_app.dto.UserDTO;
import com.example.reminder_app.entity.UserEntity;
import com.example.reminder_app.mapper.UserMapper;
import com.example.reminder_app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/local_registration")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserDTO> createNewUser(@RequestBody @Valid UserDTO userDTO) {
        UserEntity savedUser = userService.createNewUser(userDTO);
        return ResponseEntity.ok(UserMapper.INSTANCE.toDto(savedUser));
    }
}