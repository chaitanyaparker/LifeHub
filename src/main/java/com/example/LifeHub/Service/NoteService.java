package com.example.LifeHub.Service;

import com.example.LifeHub.DTO.Request.CreateNoteRequestDTO;
import com.example.LifeHub.DTO.Request.UpdateNoteRequestDTO;
import com.example.LifeHub.DTO.Response.NoteResponseDTO;

import java.util.List;

public interface NoteService {

    NoteResponseDTO createNote(Long userId, CreateNoteRequestDTO request);

    NoteResponseDTO updateNote(Long userId, Long noteId, UpdateNoteRequestDTO request);

    void deleteNote(Long userId, Long noteId);

    NoteResponseDTO getNoteById(Long userId, Long noteId);

    List<NoteResponseDTO> getAllNotes(Long userId);

    NoteResponseDTO togglePin(Long userId, Long noteId);

    NoteResponseDTO toggleFavorite(Long userId, Long noteId);
}
