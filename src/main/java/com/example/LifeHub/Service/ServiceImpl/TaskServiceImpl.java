package com.example.LifeHub.Service.ServiceImpl;

import com.example.LifeHub.DTO.Request.TaskRequestDTO;
import com.example.LifeHub.DTO.Response.TaskResponseDTO;
import com.example.LifeHub.Entity.Tasks;
import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Repository.TaskRepository;
import com.example.LifeHub.Repository.UserRepository;
import com.example.LifeHub.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public TaskResponseDTO createTask(Long userId, TaskRequestDTO taskRequestDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        Tasks task = toEntity(taskRequestDTO);
        task.setUser(user);

        Tasks savedTask = taskRepository.save(task);

        return toResponse(savedTask);
    }

    @Override
    public TaskResponseDTO getTaskById(Long taskId) {

        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task Not Found"));

        return toResponse(task);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks(Long userId) {

        return taskRepository.findByUserUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO taskRequestDTO) {

        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task Not Found"));

        task.setTaskName(taskRequestDTO.getTaskName());
        task.setTaskPriority(taskRequestDTO.getTaskPriority());
        task.setTaskStartTime(taskRequestDTO.getTaskStartTime());
        task.setTaskEndTime(taskRequestDTO.getTaskEndTime());
        task.setTaskStatus(taskRequestDTO.getTaskStatus());
        task.setTaskCategory(taskRequestDTO.getTaskCategory());
        task.setTaskDescription(taskRequestDTO.getTaskDescription());

        Tasks updatedTask = taskRepository.save(task);

        return toResponse(updatedTask);
    }

    @Override
    public void deleteTask(Long taskId) {

        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task Not Found"));

        taskRepository.delete(task);
    }

    private Tasks toEntity(TaskRequestDTO taskRequestDTO) {
        Tasks task = new Tasks();

        task.setTaskName(taskRequestDTO.getTaskName());
        task.setTaskPriority(taskRequestDTO.getTaskPriority());
        task.setTaskStartTime(taskRequestDTO.getTaskStartTime());
        task.setTaskEndTime(taskRequestDTO.getTaskEndTime());
        task.setTaskStatus(taskRequestDTO.getTaskStatus());
        task.setTaskCategory(taskRequestDTO.getTaskCategory());
        task.setTaskDescription(taskRequestDTO.getTaskDescription());

        return task;
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
