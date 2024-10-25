package com.example.reminder_app.config.telegram;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BotConfig {
    @Value("$bot_name")
    String botName;
    @Value("${bot_token}")
    String token;
}
