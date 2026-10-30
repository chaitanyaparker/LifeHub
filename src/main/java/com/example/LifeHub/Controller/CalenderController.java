package com.example.LifeHub.Controller;

import com.example.LifeHub.DTO.Calender.RequestDTO.CalenderRequestDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.CalendarMonthResponseDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.CalenderResponseDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.TodayAgendaResponseDTO;
import com.example.LifeHub.DTO.Calender.ResponseDTO.UpcomingEventDTO;
import com.example.LifeHub.Security.custom.CustomUserDetail;
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
            @AuthenticationPrincipal CustomUserDetail userDetails,
            @Valid @RequestBody CalenderRequestDTO requestDTO) {

        Long userId = userDetails.getUserId();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(calenderService.createEvent(userId, requestDTO));
    }

    @GetMapping
    public ResponseEntity<List<CalenderResponseDTO>> getAllEvents(
            @AuthenticationPrincipal CustomUserDetail userDetails) {

        Long userId = userDetails.getUserId();

        return ResponseEntity.ok(calenderService.getAllEvents(userId));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<CalenderResponseDTO> getEventById(
            @AuthenticationPrincipal CustomUserDetail userDetails,
            @PathVariable Long eventId) {

        Long userId = userDetails.getUserId();

        return ResponseEntity.ok(
                calenderService.getEventById(userId, eventId)
        );
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<CalenderResponseDTO> updateEvent(
            @AuthenticationPrincipal CustomUserDetail userDetails,
            @PathVariable Long eventId,
            @Valid @RequestBody CalenderRequestDTO requestDTO) {

        Long userId = userDetails.getUserId();

        return ResponseEntity.ok(
                calenderService.updateEvent(userId, eventId, requestDTO)
        );
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal CustomUserDetail userDetails,
            @PathVariable Long eventId) {

        Long userId = userDetails.getUserId();
        calenderService.deleteEvent(userId, eventId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/month")
    public ResponseEntity<CalendarMonthResponseDTO> getMonthEvents(
            @AuthenticationPrincipal CustomUserDetail userDetails,
            @RequestParam Integer year,
            @RequestParam Integer month) {

        Long userId = userDetails.getUserId();

        return ResponseEntity.ok(
                calenderService.getMonthEvents(userId, year, month)
        );
    }

    @GetMapping("/today")
    public ResponseEntity<TodayAgendaResponseDTO> getTodayAgenda(
            @AuthenticationPrincipal CustomUserDetail userDetails) {

        Long userId = userDetails.getUserId();

        return ResponseEntity.ok(
                calenderService.getTodayEvents(userId)
        );
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<UpcomingEventDTO>> getUpcomingEvents(
            @AuthenticationPrincipal CustomUserDetail userDetails) {

        Long userId = userDetails.getUserId();

        return ResponseEntity.ok(
                calenderService.getUpcomingEvents(userId)
        );
    }
}