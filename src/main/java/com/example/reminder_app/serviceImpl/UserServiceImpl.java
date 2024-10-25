package com.example.reminder_app.serviceImpl;

import com.example.reminder_app.dto.UserDTO;
import com.example.reminder_app.entity.UserEntity;
import com.example.reminder_app.mapper.UserMapper;
import com.example.reminder_app.repository.UserRepository;
import com.example.reminder_app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserEntity createNewUser(UserDTO userDTO) {

        UserEntity user = UserMapper.INSTANCE.fromDto(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);

    }



    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
    }


}
