package com.example.LifeHub.DTO.Calender.ResponseDTO;

import com.example.LifeHub.Enums.EventCategory;
import com.example.LifeHub.Enums.EventStatus;
import com.example.LifeHub.Enums.RepeatType;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
public class CalenderResponseDTO {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean allDay;

    private String location;

    private String color;

    private EventCategory category;

    private RepeatType repeatType;

    private LocalDateTime reminderMinutes;

    private EventStatus status;

    private boolean isCompleted;

    private LocalDateTime duration;

    private Instant createdAt;

    private Instant updatedAt;
}
