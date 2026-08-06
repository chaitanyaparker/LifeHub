package com.example.LifeHub.Repository;

import com.example.LifeHub.Entity.CalendarEvent;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Enums.EventCategory;
import com.example.LifeHub.Enums.EventStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<CalendarEvent, Long> {

    Optional<CalendarEvent> findByIdAndUser(Long id, User user);

    List<CalendarEvent> findByUser(User user);

    List<CalendarEvent> findByUserAndStartTimeBetween(
            User user,
            LocalDateTime start,
            LocalDateTime end
    );

    List<CalendarEvent> findByUserAndStartTimeBetweenOrderByStartTimeAsc(
            User user,
            LocalDateTime start,
            LocalDateTime end
    );

    List<CalendarEvent> findTop5ByUserAndStartTimeAfterOrderByStartTimeAsc(
            User user,
            LocalDateTime currentTime
    );

    List<CalendarEvent> findByUserAndCategory(
            User user,
            EventCategory category
    );

    List<CalendarEvent> findByUserAndStatus(
            User user,
            EventStatus status
    );

    List<CalendarEvent> findByUserAndTitleContainingIgnoreCase(
            User user,
            String keyword
    );

    boolean existsByUserAndTitleAndStartTime(
            User user,
            String title,
            LocalDateTime startTime
    );

    List<CalendarEvent> findByUserAndStartTimeAfterOrderByStartTimeAsc(
            User user,
            LocalDateTime startTime,
            Pageable pageable
    );
}