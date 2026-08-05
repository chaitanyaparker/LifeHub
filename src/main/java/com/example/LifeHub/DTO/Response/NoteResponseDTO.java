package com.example.LifeHub.DTO.Response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteResponseDTO {

    private Long id;

    private String title;

    private JsonNode content;

    private boolean pinned;

    private boolean favorite;

    private Instant createdAt;

    private Instant updatedAt;
}