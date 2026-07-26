package com.example.LifeHub.DTO.Request;

import com.example.LifeHub.Enums.TaskCategory;
import com.example.LifeHub.Enums.TaskPriority;
import com.example.LifeHub.Enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequestDTO {

    @NotBlank(message = "taskName is required")
    private String taskName;

    @NotBlank(message = "Category is required")
    private TaskCategory taskCategory;

    @NotBlank(message = "Status is required")
    private TaskStatus taskStatus;

    @NotBlank(message = "Priority is required")
    private TaskPriority taskPriority;

    private String taskDescription;

    @NotBlank(message = "Start is required")
    private LocalDateTime taskStartTime;

    @NotBlank(message = "End is required")
    private LocalDateTime taskEndTime;
}
