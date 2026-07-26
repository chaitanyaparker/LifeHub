package com.example.LifeHub.DTO.Response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaskListResponseDTO {

    private long totalTasks;

    private long pendingTasks;

    private long inProgressTasks;

    private long completedTasks;

    private long overdueTasks;

    private List<TaskResponseDTO> tasks;
}