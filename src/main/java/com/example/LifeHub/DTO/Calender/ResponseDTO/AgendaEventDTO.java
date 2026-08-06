package com.example.LifeHub.DTO.Calender.ResponseDTO;

import com.example.LifeHub.Enums.EventCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaEventDTO {

    private Long id;

    private String title;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String duration;

    private String color;

    private EventCategory category;
}