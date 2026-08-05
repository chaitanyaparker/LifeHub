package com.example.LifeHub.Repository;

import com.example.LifeHub.Entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findByIdAndUserUserId(Long noteId, Long userId);

    List<Note> findAllByUserUserId(Long userId);

    List<Note> findAllByUserUserIdAndPinnedTrue(Long userId);

    List<Note> findAllByUserUserIdAndFavoriteTrue(Long userId);

    boolean existsByIdAndUserUserId(Long noteId, Long userId);
}