package com.example.reminder_app.config.emailSendler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${email_username}")
    private String emailUserName;
    @Value("${email_password}")
    private String emailPassword;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        sender.setUsername(emailUserName);
        sender.setPassword(emailPassword);
        sender.setJavaMailProperties(javaMailProperties());
        return sender;
    }

    @Bean
    public Properties javaMailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        return properties;
    }

    @Bean
    public EmailService emailService() {
        return new EmailServiceImpl(javaMailSender());
    }
}
