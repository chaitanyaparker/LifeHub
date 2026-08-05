package com.example.LifeHub.DTO.Request;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNoteRequestDTO {

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 3, max = 255)
    private String title;

    private JsonNode content;
}
