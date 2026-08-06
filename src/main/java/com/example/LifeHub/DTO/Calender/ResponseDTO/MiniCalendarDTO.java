package com.example.LifeHub.DTO.Calender.ResponseDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MiniCalendarDTO {

    private Integer month;

    private Integer year;

    private Integer selectedDate;
}