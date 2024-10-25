package com.example.reminder_app.service;

import com.example.reminder_app.config.emailSendler.EmailService;
import com.example.reminder_app.dto.ReminderDTO;
import com.example.reminder_app.entity.ReminderEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface ReminderService extends EmailService {

    ReminderEntity createReminder(ReminderDTO dto);

    void deleteReminder(Long id);

    List<ReminderEntity> sortReminders(String sortBy);


    Page<ReminderDTO> searchReminders(int page, int size, String title, String description, LocalDateTime dateTime);

    List<ReminderEntity> filterReminders(String date, String time);

    List<ReminderEntity> getAllReminders();
}
