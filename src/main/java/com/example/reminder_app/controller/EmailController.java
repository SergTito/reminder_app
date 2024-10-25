package com.example.reminder_app.controller;

import com.example.reminder_app.config.emailSendler.EmailService;
import com.example.reminder_app.entity.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;



    @PostMapping("/simple")
    public void sendSimpleEmail(@RequestBody Mail mail){
        emailService.sendSimpleEmail(mail);
    }


}
