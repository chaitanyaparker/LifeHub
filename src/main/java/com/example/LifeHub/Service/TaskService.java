package com.example.LifeHub.Service;

import com.example.LifeHub.DTO.Request.TaskRequestDTO;
import com.example.LifeHub.DTO.Response.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(Long userId, TaskRequestDTO taskRequestDTO);

    TaskResponseDTO getTaskById(Long taskId);

    List<TaskResponseDTO> getAllTasks(Long userId);

    TaskResponseDTO updateTask(Long taskId, TaskRequestDTO taskRequestDTO);

    void deleteTask(Long taskId);
}
