package com.example.reminder_app.util;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private ZonedDateTime dateTime;
}
