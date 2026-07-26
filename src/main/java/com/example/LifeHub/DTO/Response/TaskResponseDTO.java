package com.example.LifeHub.DTO.Response;

import com.example.LifeHub.Enums.TaskCategory;
import com.example.LifeHub.Enums.TaskPriority;
import com.example.LifeHub.Enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {

    private Long taskId;

    private String title;

    private String description;

    private LocalDateTime dueDate;

    private TaskPriority priority;

    private TaskStatus status;

    private TaskCategory category;
}