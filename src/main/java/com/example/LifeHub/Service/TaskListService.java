package com.example.LifeHub.Service;

import com.example.LifeHub.DTO.Response.TaskListResponseDTO;

public interface TaskListService {

    TaskListResponseDTO getAllTasks(Long userId);
}
