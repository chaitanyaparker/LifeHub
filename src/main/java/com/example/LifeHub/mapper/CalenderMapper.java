package com.example.LifeHub.mapper;

import com.example.LifeHub.DTO.Calender.RequestDTO.CalenderRequestDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.*;
import com.example.LifeHub.Entity.CalendarEvent;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public final class CalenderMapper {

    private CalenderMapper() {
    }

    public static CalendarEvent toEntity(CalenderRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        return CalendarEvent.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .allDay(dto.isAllDay())
                .location(dto.getLocation())
                .color(dto.getColor())
                .category(dto.getCategory())
                .repeatType(dto.getRepeatType())
                .reminderMinutes(dto.getReminderMinutes())
                .build();
    }

    // ==========================================================
    // Entity -> ResponseDTO
    // ==========================================================

    public static CalenderResponseDTO toResponseDTO(CalendarEvent event) {

        if (event == null) {
            return null;
        }

        return CalenderResponseDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .description(event.getDescription())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .allDay(event.isAllDay())
                .location(event.getLocation())
                .color(event.getColor())
                .category(event.getCategory())
                .repeatType(event.getRepeatType())
                .reminderMinutes(event.getReminderMinutes())
                .status(event.getStatus())
                .createdAt(event.getCreatedAt())
                .updatedAt(event.getUpdatedAt())
                .build();
    }

    // ==========================================================
    // Entity List -> ResponseDTO List
    // ==========================================================

    public static List<CalenderResponseDTO> toResponseDTOList(List<CalendarEvent> events) {

        return events.stream()
                .map(CalenderMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ==========================================================
    // Update Entity
    // ==========================================================

    public static void updateEntity(CalendarEvent event,
                                    CalenderRequestDTO dto) {

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setAllDay(dto.isAllDay());
        event.setLocation(dto.getLocation());
        event.setColor(dto.getColor());
        event.setCategory(dto.getCategory());
        event.setRepeatType(dto.getRepeatType());
        event.setReminderMinutes(dto.getReminderMinutes());
    }

    // ==========================================================
    // Entity -> Agenda DTO
    // ==========================================================

    public static AgendaEventDTO toAgendaEventDTO(CalendarEvent event) {

        if (event == null) {
            return null;
        }

        return AgendaEventDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .duration(calculateDuration(event.getStartTime(), event.getEndTime()))
                .color(event.getColor())
                .category(event.getCategory())
                .build();
    }

    public static List<AgendaEventDTO> toAgendaEventDTOList(List<CalendarEvent> events) {

        return events.stream()
                .map(CalenderMapper::toAgendaEventDTO)
                .collect(Collectors.toList());
    }

    // ==========================================================
    // Entity -> Upcoming DTO
    // ==========================================================

    public static UpcomingEventDTO toUpcomingEventDTO(CalendarEvent event) {

        if (event == null) {
            return null;
        }

        return UpcomingEventDTO.builder()
                .id(event.getId())
                .title(event.getTitle())
                .startTime(event.getStartTime())
                .category(event.getCategory())
                .color(event.getColor())
                .build();
    }

    public static List<UpcomingEventDTO> toUpcomingEventDTOList(List<CalendarEvent> events) {

        return events.stream()
                .map(CalenderMapper::toUpcomingEventDTO)
                .collect(Collectors.toList());
    }


    public static CalendarDayDTO toCalendarDayDTO(
            LocalDate date,
            List<CalenderResponseDTO> events,
            boolean currentMonth,
            boolean today,
            boolean selected
    ) {

        return CalendarDayDTO.builder()
                .date(date)
                .dayOfWeek(date.getDayOfWeek().name())
                .currentMonth(currentMonth)
                .today(today)
                .selected(selected)
                .events(events)
                .build();
    }

    public static CalendarMonthResponseDTO toCalendarMonthResponseDTO(
            Integer year,
            Integer month,
            Integer days
    ) {

        return CalendarMonthResponseDTO.builder()
                .year(year)
                .month(month)
                .days(days)
                .build();
    }


    public static TodayAgendaResponseDTO toTodayAgendaResponseDTO(
            LocalDate date,
            List<AgendaEventDTO> events
    ) {

        return TodayAgendaResponseDTO.builder()
                .date(date)
                .events(events)
                .build();
    }


    private static String calculateDuration(
            java.time.LocalDateTime start,
            java.time.LocalDateTime end
    ) {

        Duration duration = Duration.between(start, end);

        long hours = duration.toHours();

        long minutes = duration.toMinutes() % 60;

        if (hours == 0) {
            return minutes + " min";
        }

        return hours + "h " + minutes + "m";
    }
}