package com.example.LifeHub.Controller;

import com.example.LifeHub.DTO.Calender.RequestDTO.CalenderRequestDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.CalendarMonthResponseDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.CalenderResponseDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.TodayAgendaResponseDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.UpcomingEventDTO;
import com.example.LifeHub.Service.CalenderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalenderController {

    private final CalenderService calenderService;

    @PostMapping
    public ResponseEntity<CalenderResponseDTO> createEvent(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody CalenderRequestDTO requestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(calenderService.createEvent(userId, requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<CalenderResponseDTO>> getAllEvents(
            @AuthenticationPrincipal Long userId) {

        return ResponseEntity.ok(calenderService.getAllEvents(userId));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<CalenderResponseDTO> getEventById(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                calenderService.getEventById(userId, eventId)
        );
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<CalenderResponseDTO> updateEvent(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody CalenderRequestDTO requestDTO) {

        return ResponseEntity.ok(
                calenderService.updateEvent(userId, eventId, requestDTO)
        );
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long eventId) {

        calenderService.deleteEvent(userId, eventId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/month")
    public ResponseEntity<CalendarMonthResponseDTO> getMonthEvents(
            @AuthenticationPrincipal Long userId,
            @RequestParam Integer year,
            @RequestParam Integer month) {

        return ResponseEntity.ok(
                calenderService.getMonthEvents(userId, year, month)
        );
    }

    @GetMapping("/today")
    public ResponseEntity<TodayAgendaResponseDTO> getTodayAgenda(
            @AuthenticationPrincipal Long userId) {

        return ResponseEntity.ok(
                calenderService.getTodayEvents(userId)
        );
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<UpcomingEventDTO>> getUpcomingEvents(
            @AuthenticationPrincipal Long userId) {

        return ResponseEntity.ok(
                calenderService.getUpcomingEvents(userId)
        );
    }
}