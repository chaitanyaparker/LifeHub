package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.DTO.Calender.RequestDTO.CalenderRequestDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.CalendarMonthResponseDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.CalenderResponseDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.TodayAgendaResponseDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.UpcomingEventDTO;
import com.example.LifeHub.Entity.CalendarEvent;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Enums.NotificationType;
import com.example.LifeHub.Exception.UserNotFoundException;
import com.example.LifeHub.Repository.CalendarRepository;
import com.example.LifeHub.Repository.UserRepository;
import com.example.LifeHub.Service.CalenderService;
import com.example.LifeHub.mapper.CalenderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalenderServiceImpl implements CalenderService {

    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;
    private final NotificationProducer notificationProducer;

    @Override
    public CalenderResponseDTO createEvent(Long userId, CalenderRequestDTO calenderRequestDTO) {

        System.out.println("========== CALENDAR SERVICE HIT ==========");
        System.out.println("User ID: " + userId);
        System.out.println("Event title: " + calenderRequestDTO.getTitle());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        CalendarEvent calendarEvent = CalenderMapper.toEntity(calenderRequestDTO);

        calendarEvent.setUser(user);


        calendarEvent.setUser(user);

        if (calendarEvent.getStatus() == null) {
            calendarEvent.setStatus(
                    com.example.LifeHub.Enums.EventStatus.UPCOMING
            );
        }

        CalendarEvent savedEvent = calendarRepository.save(calendarEvent);

//        com.example.LifeHub.Notification.NotificationEvent notification = com.example.LifeHub.Notification.NotificationEvent.builder()
//                .userId(user.getUserId())
//                .title("Calendar Event Created")
//                .message("Your calendar event '" + savedEvent.getTitle()
//                        + "' has been created successfully.")
//                .type(NotificationType.CALENDAR)
//                .build();
//
//        notificationProducer.sendNotification(notification);

        return CalenderMapper.toResponseDTO(savedEvent);
    }

    @Override
    public List<CalenderResponseDTO> getAllEvents(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<CalendarEvent> events = calendarRepository.findByUser(user);

        return events.stream()
                .map(CalenderMapper::toResponseDTO)
                .toList();
    }

    @Override
    public CalenderResponseDTO getEventById(Long userId, Long eventId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        CalendarEvent event = calendarRepository.findByIdAndUser(eventId, user)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return CalenderMapper.toResponseDTO(event);
    }

    @Override
    public CalenderResponseDTO updateEvent(Long userId,
                                           Long eventId,
                                           CalenderRequestDTO requestDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        CalendarEvent event = calendarRepository.findByIdAndUser(eventId, user)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setTitle(requestDTO.getTitle());
        event.setDescription(requestDTO.getDescription());
        event.setLocation(requestDTO.getLocation());
        event.setStartTime(requestDTO.getStartTime());
        event.setEndTime(requestDTO.getEndTime());
        event.setAllDay(requestDTO.isAllDay());
        event.setColor(requestDTO.getColor());
        event.setCategory(requestDTO.getCategory());
        event.setReminderMinutes(requestDTO.getReminderMinutes());
        event.setStatus(requestDTO.getEventStatus());

        if (requestDTO.getEventStatus() != null) {
            event.setStatus(requestDTO.getEventStatus());
        }

        CalendarEvent updatedEvent = calendarRepository.save(event);

        com.example.LifeHub.Notification.NotificationEvent notification = com.example.LifeHub.Notification.NotificationEvent.builder()
                .userId(user.getUserId())
                .title("Calendar Event Updated")
                .message("Your calendar event '" + updatedEvent.getTitle()
                        + "' has been updated successfully.")
                .type(NotificationType.CALENDAR)
                .build();

        notificationProducer.sendNotification(notification);

        return CalenderMapper.toResponseDTO(updatedEvent);
    }

    @Override
    public void deleteEvent(Long userId, Long eventId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        CalendarEvent event = calendarRepository.findByIdAndUser(eventId, user)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        String eventTitle = event.getTitle();

        calendarRepository.delete(event);

        com.example.LifeHub.Notification.NotificationEvent notification = com.example.LifeHub.Notification.NotificationEvent.builder()
                .userId(user.getUserId())
                .title("Calendar Event Deleted")
                .message("Your calendar event '" + eventTitle
                        + "' has been deleted successfully.")
                .type(NotificationType.CALENDAR)
                .build();

        notificationProducer.sendNotification(notification);
    }

    @Override
    public CalendarMonthResponseDTO getMonthEvents(Long userId,
                                                   Integer year,
                                                   Integer month) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        LocalDateTime start = LocalDate.of(year, month, 1)
                .atStartOfDay();

        LocalDateTime end = start.plusMonths(1);

        List<CalendarEvent> events = calendarRepository
                .findByUserAndStartTimeBetween(user, start, end);

        return CalendarMonthResponseDTO.builder()
                .year(year)
                .month(month)
                .events(events.stream()
                        .map(CalenderMapper::toResponseDTO)
                        .toList())
                .build();
    }

    @Override
    public TodayAgendaResponseDTO getTodayEvents(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        List<CalendarEvent> events = calendarRepository
                .findByUserAndStartTimeBetween(user, start, end);

        return CalenderMapper.toTodayAgendaResponseDTO(
                LocalDate.now(),
                CalenderMapper.toAgendaEventDTOList(events)
        );
    }

    @Override
    public List<UpcomingEventDTO> getUpcomingEvents(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Pageable pageable = PageRequest.of(0, 5);

        return calendarRepository
                .findByUserAndStartTimeAfterOrderByStartTimeAsc(
                        user,
                        LocalDateTime.now(),
                        pageable
                )
                .stream()
                .map(CalenderMapper::toUpcomingEventDTO)
                .toList();
    }
}
