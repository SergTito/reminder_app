package com.example.reminder_app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReminderDTO {

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @FutureOrPresent(message = "Cannot set past tense")
    private LocalDateTime remind;

    private ReminderStatus status;//not implemented



}
