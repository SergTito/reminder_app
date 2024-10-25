package com.example.reminder_app.dto;

public enum ReminderStatus {
    ACTIVE("Actively"),
    COMPLETED("Done"),
    OVERDUE("Time limit exceeded"),
    PENDING("Pending");

    private final String description;

    ReminderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
