package com.example.LifeHub.DTO.Calender.ResponseDTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodayAgendaResponseDTO {

    private LocalDate date;

    private List<AgendaEventDTO> events;
}