package com.example.LifeHub.Controller;

import com.example.LifeHub.DTO.Request.CreateNoteRequestDTO;
import com.example.LifeHub.DTO.Request.UpdateNoteRequestDTO;
import com.example.LifeHub.DTO.Response.NoteResponseDTO;
import com.example.LifeHub.Service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponseDTO> createNote(
            @RequestParam Long userId,
            @RequestBody CreateNoteRequestDTO request
    ) {
        NoteResponseDTO createdNote = noteService.createNote(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteResponseDTO> updateNote(
            @RequestParam Long userId,
            @PathVariable Long noteId,
            @RequestBody UpdateNoteRequestDTO request
    ) {
        NoteResponseDTO updatedNote = noteService.updateNote(userId, noteId, request);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(
            @RequestParam Long userId,
            @PathVariable Long noteId
    ) {
        noteService.deleteNote(userId, noteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponseDTO> getNoteById(
            @RequestParam Long userId,
            @PathVariable Long noteId
    ) {
        NoteResponseDTO note = noteService.getNoteById(userId, noteId);
        return ResponseEntity.ok(note);
    }

    @GetMapping
    public ResponseEntity<List<NoteResponseDTO>> getAllNotes(
            @RequestParam Long userId
    ) {
        List<NoteResponseDTO> notes = noteService.getAllNotes(userId);
        return ResponseEntity.ok(notes);
    }

    @PatchMapping("/{noteId}/pin")
    public ResponseEntity<NoteResponseDTO> togglePin(
            @RequestParam Long userId,
            @PathVariable Long noteId
    ) {
        NoteResponseDTO updatedNote = noteService.togglePin(userId, noteId);
        return ResponseEntity.ok(updatedNote);
    }

    @PatchMapping("/{noteId}/favorite")
    public ResponseEntity<NoteResponseDTO> toggleFavorite(
            @RequestParam Long userId,
            @PathVariable Long noteId
    ) {
        NoteResponseDTO updatedNote = noteService.toggleFavorite(userId, noteId);
        return ResponseEntity.ok(updatedNote);
    }
}