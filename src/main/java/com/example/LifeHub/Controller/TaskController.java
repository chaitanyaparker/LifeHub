package com.example.LifeHub.Controller;

import com.example.LifeHub.DTO.Request.TaskRequestDTO;
import com.example.LifeHub.DTO.Response.TaskListResponseDTO;
import com.example.LifeHub.DTO.Response.TaskResponseDTO;
import com.example.LifeHub.Service.TaskListService;
import com.example.LifeHub.Service.TaskService;
import com.example.LifeHub.common.APIResponse;
import com.example.LifeHub.common.APIResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskListService taskListService;

    @PostMapping("/{userId}")
    public ResponseEntity<APIResponse<TaskResponseDTO>> createTask(
            @PathVariable Long userId,
            @Valid @RequestBody TaskRequestDTO taskRequestDTO
    ) {

        TaskResponseDTO response = taskService.createTask(userId, taskRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponseUtil.success("Task Created Successfully", response));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<APIResponse<TaskResponseDTO>> getTaskById(
            @PathVariable Long taskId
    ) {

        TaskResponseDTO response = taskService.getTaskById(taskId);

        return ResponseEntity.ok(
                APIResponseUtil.success("Task Found Successfully", response)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<APIResponse<List<TaskResponseDTO>>> getAllTasks(
            @PathVariable Long userId
    ) {

        List<TaskResponseDTO> response = taskService.getAllTasks(userId);

        return ResponseEntity.ok(
                APIResponseUtil.success("Tasks Found Successfully", response)
        );
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<APIResponse<TaskResponseDTO>> updateTask(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskRequestDTO taskRequestDTO
    ) {

        TaskResponseDTO response = taskService.updateTask(taskId, taskRequestDTO);

        return ResponseEntity.ok(
                APIResponseUtil.success("Task Updated Successfully", response)
        );
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<APIResponse<Void>> deleteTask(
            @PathVariable Long taskId
    ) {

        taskService.deleteTask(taskId);

        return ResponseEntity.ok(
                APIResponseUtil.success("Task Deleted Successfully", null)
        );
    }

    @GetMapping
    public ResponseEntity<APIResponse<TaskListResponseDTO>> getAllTasksCount(
            @RequestParam Long userId
    ) {

        TaskListResponseDTO taskListResponseDTO =
                taskListService.getAllTasks(userId);

        return ResponseEntity.ok(
                APIResponseUtil.success(
                        "Tasks Fetched Successfully",
                        taskListResponseDTO
                )
        );
    }
}