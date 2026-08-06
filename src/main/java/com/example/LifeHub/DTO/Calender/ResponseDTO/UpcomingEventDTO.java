package com.example.LifeHub.DTO.Calender.ResponseDTO;

import com.example.LifeHub.Enums.EventCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpcomingEventDTO {

    private Long id;

    private String title;

    private LocalDateTime startTime;

    private EventCategory category;

    private String color;
}