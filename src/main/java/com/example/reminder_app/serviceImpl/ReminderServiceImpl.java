package com.example.reminder_app.serviceImpl;


import com.example.reminder_app.config.user.UserInfo;
import com.example.reminder_app.dto.ReminderDTO;
import com.example.reminder_app.entity.Mail;
import com.example.reminder_app.entity.ReminderEntity;
import com.example.reminder_app.entity.UserEntity;
import com.example.reminder_app.mapper.ReminderMapper;
import com.example.reminder_app.repository.ReminderRepository;
import com.example.reminder_app.repository.UserRepository;
import com.example.reminder_app.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;
    private final UserInfo userInfo;
    private final JavaMailSender mailSender;



    @Override
    @Transactional
    public ReminderEntity createReminder(ReminderDTO dto) {
        String userId = userInfo.getCurrentUser().getName();
        Optional<UserEntity> userById = userRepository.findUserById(Long.valueOf(userId));

        ReminderEntity reminder = ReminderMapper.INSTANCE.fromDto(dto);
        reminder.setUser(userById.orElseThrow(() -> new UsernameNotFoundException("User not found")));

        sendReminderEmail(reminder);


        return reminderRepository.save(reminder);
    }

    private boolean isRemindingTime(ReminderEntity reminder) {
        return reminder.getRemind().equals(LocalDateTime.now());
    }
    private void sendReminderEmail(ReminderEntity reminder) {
        if (isRemindingTime(reminder)) {
            Mail mail = createReminderMail(reminder);
            sendSimpleEmail(mail);
        }
    }
    private Mail createReminderMail(ReminderEntity reminder) {
        String email = reminder.getUser().getEmail();
        String title = reminder.getTitle();
        String body = "Remind";
        return new Mail(email, title, body);
    }


    @Override
    @Transactional
    public void deleteReminder(Long id) {
        reminderRepository.deleteById(id);
    }


    @Override
    @Transactional
    public Page<ReminderDTO> searchReminders(int page, int size, String title, String description, LocalDateTime dateTime) {
        currentUser();

        Pageable pageable = PageRequest.of(page, size);
        List<ReminderEntity> reminders = reminderRepository.findAllByTitleAndDescriptionAndRemind(
                title, description, dateTime, pageable);

        return new PageImpl<>(reminders.stream()
                .map(this::toDto)
                .collect(Collectors.toList()),
                pageable,
                reminders.size());
    }


    @Override
    @Transactional
    public List<ReminderEntity> sortReminders(String sortBy) {
        currentUser();

        if ("title".equalsIgnoreCase(sortBy)) {
            return reminderRepository.findAll().stream()
                    .sorted(Comparator.comparing(ReminderEntity::getTitle))
                    .collect(Collectors.toList());
        } else if ("remind".equalsIgnoreCase(sortBy)) {
            return reminderRepository.findAll().stream()
                    .sorted(Comparator.comparing(ReminderEntity::getRemind))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Invalid collation");
        }
    }


    @Override
    @Transactional
    public List<ReminderEntity> filterReminders(String date, String time) {
        currentUser();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = date != null ? LocalDate.parse(date, formatter) : null;
        LocalTime localTime = time != null ? LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")) : null;

        return reminderRepository.findAll().stream()
                .filter(r -> {
                    if (localDate != null && r.getRemind().toLocalDate() != localDate) {
                        return false;
                    }
                    return localTime == null || r.getRemind().toLocalTime() == localTime;
                })
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public List<ReminderEntity> getAllReminders() {
        String userId = userInfo.getCurrentUser().getName();
        Optional<UserEntity> userById = userRepository.findUserById(Long.valueOf(userId));
        if (userById.isPresent()) {
            return reminderRepository.findAllByUserId(userById.get().getId());
        }
        throw new UsernameNotFoundException("User not found");
    }


    private ReminderDTO toDto(ReminderEntity entity) {
        return ReminderMapper.INSTANCE.toDto(entity);
    }

    private void currentUser() {
        String userId = userInfo.getCurrentUser().getName();
        userRepository.findUserById(Long.valueOf(userId)).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    @Async
    public void sendSimpleEmail(Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getBody());
        mailSender.send(mailMessage);
    }
}
