package com.example.LifeHub.mapper;

import com.example.LifeHub.DTO.Request.CreateNoteRequestDTO;
import com.example.LifeHub.DTO.Request.UpdateNoteRequestDTO;
import com.example.LifeHub.DTO.Response.NoteResponseDTO;
import com.example.LifeHub.Entity.Note;

public class NoteMapper {

    private NoteMapper() {
    }

    public static Note toEntity(CreateNoteRequestDTO request) {
        return Note.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    public static NoteResponseDTO toResponseDTO(Note note) {
        return NoteResponseDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .pinned(note.isPinned())
                .favorite(note.isFavorite())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build();
    }

    public static Note updateEntity(Note note, UpdateNoteRequestDTO request) {
        note.setTitle(request.getTitle());
        note.setContent(request.getContent());
        return note;
    }
}