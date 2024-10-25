package com.example.reminder_app.functionalTest;

import com.example.reminder_app.config.user.UserInfo;
import com.example.reminder_app.repository.ReminderRepository;
import com.example.reminder_app.repository.UserRepository;
import com.example.reminder_app.serviceImpl.ReminderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;


public class ReminderServiceImplTest {


    @Mock
    private UserInfo userInfo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReminderRepository reminderRepository;

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private ReminderServiceImpl reminderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


}
