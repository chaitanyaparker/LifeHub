package com.example.LifeHub.DTO.Calender.RequestDTO;

import com.example.LifeHub.Enums.EventCategory;
import com.example.LifeHub.Enums.EventStatus;
import com.example.LifeHub.Enums.RepeatType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalenderRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Builder.Default
    private boolean allDay = false;

    private String location;

    private String color;

    private EventCategory category;

    private EventStatus eventStatus;

    private RepeatType repeatType;

    private LocalDateTime reminderMinutes;

}
