package com.example.reminder_app.config.emailSendler;

import com.example.reminder_app.entity.Mail;

public interface EmailService {
    void sendSimpleEmail(Mail mail);
}
