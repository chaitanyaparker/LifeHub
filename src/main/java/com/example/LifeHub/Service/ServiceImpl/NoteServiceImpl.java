package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.DTO.Request.CreateNoteRequestDTO;
import com.example.LifeHub.DTO.Request.UpdateNoteRequestDTO;
import com.example.LifeHub.DTO.Response.NoteResponseDTO;
import com.example.LifeHub.Entity.Note;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Exception.UserNotFoundException;
import com.example.LifeHub.Repository.NoteRepository;
import com.example.LifeHub.Repository.UserRepository;
import com.example.LifeHub.Service.NoteService;
import com.example.LifeHub.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Override
    public NoteResponseDTO createNote(Long userId, CreateNoteRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Note note = NoteMapper.toEntity(request);
        note.setUser(user);

        Note savedUserNote = noteRepository.save(note);

        return NoteMapper.toResponseDTO(savedUserNote);
    }

    @Override
    public NoteResponseDTO updateNote(Long userId, Long noteId, UpdateNoteRequestDTO request) {
        Note existingNote = noteRepository.findByIdAndUserUserId(noteId, userId)
                .orElseThrow(() -> new RuntimeException("Note not found for this user"));

        Note updatedNote = NoteMapper.updateEntity(existingNote, request);

        Note savedUserNote = noteRepository.save(updatedNote);

        return NoteMapper.toResponseDTO(savedUserNote);
    }

    @Override
    public void deleteNote(Long userId, Long noteId) {
        Note note = noteRepository.findByIdAndUserUserId(noteId, userId)
                .orElseThrow(() -> new RuntimeException("Note not found for this user"));

        noteRepository.delete(note);
    }

    @Override
    public NoteResponseDTO getNoteById(Long userId, Long noteId) {
        Note note = noteRepository.findByIdAndUserUserId(noteId, userId)
                .orElseThrow(() -> new RuntimeException("Note not found for this user"));

        return NoteMapper.toResponseDTO(note);
    }

    @Override
    public List<NoteResponseDTO> getAllNotes(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }

        return noteRepository.findAllByUserUserId(userId)
                .stream()
                .map(NoteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NoteResponseDTO togglePin(Long userId, Long noteId) {
        Note note = noteRepository.findByIdAndUserUserId(noteId, userId)
                .orElseThrow(() -> new RuntimeException("Note not found for this user"));

        note.setPinned(!note.isPinned());
        Note saved = noteRepository.save(note);

        return NoteMapper.toResponseDTO(saved);
    }

    @Override
    public NoteResponseDTO toggleFavorite(Long userId, Long noteId) {
        Note note = noteRepository.findByIdAndUserUserId(noteId, userId)
                .orElseThrow(() -> new RuntimeException("Note not found for this user"));

        note.setFavorite(!note.isFavorite());
        Note saved = noteRepository.save(note);

        return NoteMapper.toResponseDTO(saved);
    }
}