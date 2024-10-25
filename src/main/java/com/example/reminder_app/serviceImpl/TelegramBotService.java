package com.example.reminder_app.serviceImpl;

import com.example.reminder_app.config.telegram.BotConfig;
import com.example.reminder_app.entity.ReminderEntity;
import com.example.reminder_app.entity.UserEntity;
import com.example.reminder_app.repository.ReminderRepository;
import com.example.reminder_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




@Component
@RequiredArgsConstructor
public class TelegramBotService extends TelegramLongPollingBot {


    private final BotConfig botConfig;
    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            long chatId = update.getMessage().getChatId();

            switch (messageText.toLowerCase()) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getUserName());
                    handleEmailInput(chatId, update.getMessage().getChat().getUserName(),update);
                    break;
                case "/reminders":
                    showTodaysReminder(chatId);
                    break;
                case "/getAllReminders":
                    getAllReminders(chatId);
                    break;

                default:
                    sendMessage(chatId, "sorry command was not found");
            }


        }
    }


    private void startCommandReceived(long chatId, String userName) {
        String answer = "Hi " + userName + " nice to meet you!\nPlease enter your email address to verify your account:";
        sendMessage(chatId, answer);
    }

    @Transactional(readOnly = true)
    public void handleEmailInput(long chatId, String userName,Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        String email = update.getMessage().getText();


        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()){
            String welcomeMessage = "Welcome back, " + userName + "!You account exist. ";
            sendMessage.setText(welcomeMessage);
        }else {
            String errorMessage = "Sorry, we couldn't find your email address in our database. Would you like to register?";
            sendMessage.setText(errorMessage);
        }

    }


    private void showTodaysReminder(long chatId) {
        String answer = "";
        List<ReminderEntity> all = reminderRepository.findAll();
        for (ReminderEntity reminder : all) {
            if (reminder.getRemind().equals(LocalDateTime.now())) {
                List<ReminderEntity> todayReminder = new ArrayList<>();
                todayReminder.add(reminder);
                answer = "You have for today " + todayReminder.size() + " reminders " +
                         ": see reminder: /getAllReminders";
            } else {
                answer = "There is nothing for today ";
            }
        }

        sendMessage(chatId, answer);
    }

    @Transactional(readOnly = true)
    public void getAllReminders(long chatId) {
        String answer = "Your reminders:\n";
        List<ReminderEntity> reminderEntities = reminderRepository.findAll();

        for (ReminderEntity reminderEntity : reminderEntities) {
            Long id = reminderEntity.getId();
            String title = reminderEntity.getTitle();
            String description = reminderEntity.getDescription();
            LocalDateTime remind = reminderEntity.getRemind();

            answer += "- ID: " + id + ", Title: " + title +
                      ", Description: " + description + ", Reminder date: " + remind + "\n";
        }

        if (reminderEntities.isEmpty()) {
            answer = "You don't have active reminders.";
        }

        sendMessage(chatId, answer);
    }

    public void sendMessage(long chatId, Object obj) {
        SendMessage msg = new SendMessage();
        msg.setChatId(chatId);
        msg.setText((String) obj);


        try {
            execute(msg);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
