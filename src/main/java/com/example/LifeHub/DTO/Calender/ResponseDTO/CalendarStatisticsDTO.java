package com.example.LifeHub.DTO.Calender.ResponseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarStatisticsDTO {

    private Integer completedTasks;

    private Integer totalTasks;

    private Double completionPercentage;
}