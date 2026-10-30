package com.example.LifeHub.Service;

import com.example.LifeHub.DTO.Calender.RequestDTO.CalenderRequestDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.CalendarMonthResponseDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.CalenderResponseDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.TodayAgendaResponseDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.UpcomingEventDTO;
import com.example.LifeHub.Enums.EventCategory;
import com.example.LifeHub.Enums.EventStatus;

import java.time.LocalDate;
import java.util.List;

public interface CalenderService {

    CalenderResponseDTO createEvent(Long userId, CalenderRequestDTO CalenderRequestDTO);

    List<CalenderResponseDTO> getAllEvents(Long userId);

    CalenderResponseDTO getEventById(Long userId, Long eventId);

    CalenderResponseDTO updateEvent(
            Long userId,
            Long eventId,
            CalenderRequestDTO CalenderRequestDTO
    );

    void deleteEvent(Long userId, Long eventId);

    CalendarMonthResponseDTO getMonthEvents(
            Long userId,
            Integer year,
            Integer month
    );

    TodayAgendaResponseDTO getTodayEvents(Long userId);

    List<UpcomingEventDTO> getUpcomingEvents(Long userId);

}