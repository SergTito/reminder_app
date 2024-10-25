package com.example.reminder_app.config.scheduler;


import com.example.reminder_app.entity.ReminderEntity;
import com.example.reminder_app.service.ReminderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReminderScheduler {


    private final ReminderService reminderService;

    public ReminderScheduler(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkReminders() {
        List<ReminderEntity> reminders = reminderService.getAllReminders();
        for (ReminderEntity reminder : reminders) {
            if (LocalDateTime.now().isAfter(reminder.getRemind())) {
                reminderService.deleteReminder(reminder.getId());
            }
        }
    }

}
