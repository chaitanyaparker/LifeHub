package com.example.LifeHub.DTO.Calender.ResponseDTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarDayDTO {

    private LocalDate date;

    private String dayOfWeek;

    private boolean currentMonth;

    private boolean today;

    private boolean selected;

    private List<CalenderResponseDTO> events;
}