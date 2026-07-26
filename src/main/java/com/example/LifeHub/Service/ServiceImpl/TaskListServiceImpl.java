package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.DTO.Response.TaskListResponseDTO;
import com.example.LifeHub.DTO.Response.TaskResponseDTO;
import com.example.LifeHub.Entity.Tasks;
import com.example.LifeHub.Enums.TaskStatus;
import com.example.LifeHub.Repository.TaskRepository;
import com.example.LifeHub.Service.TaskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    private final TaskRepository taskRepository;

    @Override
    public TaskListResponseDTO getAllTasks(Long userId) {

        List<TaskResponseDTO> taskList = taskRepository.findByUserUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();

        return TaskListResponseDTO.builder()
                .totalTasks(taskRepository.countByUserUserId(userId))
                .pendingTasks(taskRepository.countByUserUserIdAndTaskStatus(userId, TaskStatus.pending))
                .inProgressTasks(taskRepository.countByUserUserIdAndTaskStatus(userId, TaskStatus.inProgress))
                .completedTasks(taskRepository.countByUserUserIdAndTaskStatus(userId, TaskStatus.completed))
                .overdueTasks(
                        taskRepository.countByUserUserIdAndTaskStatusNotAndTaskEndTimeBefore(
                                userId,
                                TaskStatus.completed,
                                LocalDateTime.now()
                        )
                )
                .tasks(taskList)
                .build();
    }

    private TaskResponseDTO toResponse(Tasks tasks) {

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();

        taskResponseDTO.setTaskId(tasks.getTaskId());
        taskResponseDTO.setTitle(tasks.getTaskName());
        taskResponseDTO.setDescription(tasks.getTaskDescription());
        taskResponseDTO.setDueDate(tasks.getTaskEndTime());
        taskResponseDTO.setPriority(tasks.getTaskPriority());
        taskResponseDTO.setStatus(tasks.getTaskStatus());
        taskResponseDTO.setCategory(tasks.getTaskCategory());

        return taskResponseDTO;
    }
}
