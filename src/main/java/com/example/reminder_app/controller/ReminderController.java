package com.example.reminder_app.controller;



import com.example.reminder_app.config.user.UserInfo;
import com.example.reminder_app.dto.ReminderDTO;
import com.example.reminder_app.entity.ReminderEntity;
import com.example.reminder_app.mapper.ReminderMapper;
import com.example.reminder_app.repository.ReminderRepository;
import com.example.reminder_app.repository.UserRepository;
import com.example.reminder_app.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reminder")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService reminderService;
    private final UserRepository userRepository;
    private final ReminderRepository reminderRepository;
    private final UserInfo userInfo;




    @GetMapping("/reminders")
    public ResponseEntity<List<ReminderEntity>> getAllReminders() {
        String userId = userInfo.getCurrentUser().getName();
        userRepository.findUserById(Long.valueOf(userId)).orElseThrow(() -> new UsernameNotFoundException("user not found"));

        List<ReminderEntity> allReminders = reminderRepository.findAll();
        return ResponseEntity.ok(allReminders);
    }

    @PostMapping("/create")
    public ResponseEntity<ReminderDTO> createReminder(@RequestBody ReminderDTO reminderDTO) {
        ReminderEntity reminder = reminderService.createReminder(reminderDTO);
        return ResponseEntity.ok(ReminderMapper.INSTANCE.toDto(reminder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ReminderDTO>> searchReminders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) LocalDateTime dateTime) {



        Page<ReminderDTO> reminders = reminderService.searchReminders(page, size, title, description, dateTime);
        return ResponseEntity.ok(reminders);
    }

    @PutMapping("/sort/{by}")
    public ResponseEntity<List<ReminderEntity>> sortReminders(@PathVariable String by) {
        List<ReminderEntity> sortedReminders = reminderService.sortReminders(by);
        return ResponseEntity.ok(sortedReminders);
    }


    @GetMapping("/filter")
    public ResponseEntity<List<ReminderEntity>> filterReminders(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String time) {
        List<ReminderEntity> filteredReminders = reminderService.filterReminders(date, time);
        return ResponseEntity.ok(filteredReminders);
    }


}
