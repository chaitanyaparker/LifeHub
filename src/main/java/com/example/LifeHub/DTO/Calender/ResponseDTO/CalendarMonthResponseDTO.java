package com.example.LifeHub.DTO.Calender.ResponseDTO;

import com.example.LifeHub.DTO.Calender.ResponseDTO.CalendarDayDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarMonthResponseDTO {

        private Integer year;
        private Integer month;
        private List<CalenderResponseDTO> events;
}