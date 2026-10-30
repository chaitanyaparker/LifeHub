
package com.example.LifeHub.Entity;

import com.example.LifeHub.Enums.TaskCategory;
import com.example.LifeHub.Enums.TaskPriority;
import com.example.LifeHub.Enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "tasks",
        indexes = {
                @Index(
                        name = "idx_tasks_user_category",
                        columnList = "user_id, task_category"
                ),
                @Index(
                        name = "idx_tasks_user_priority",
                        columnList = "user_id, task_priority"
                ),
                @Index(
                        name = "idx_tasks_user_status",
                        columnList = "user_id, task_status"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Size(max = 200)
    @Column(name = "task_name", nullable = false, length = 200)
    private String taskName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "task_category", nullable = false, length = 40)
    private TaskCategory taskCategory;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "task_priority", nullable = false, length = 30)
    private TaskPriority taskPriority;

    @Builder.Default
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false, length = 30)
    private TaskStatus taskStatus = TaskStatus.pending;

    @NotBlank
    @Column(name = "task_description", nullable = false, columnDefinition = "TEXT")
    private String taskDescription;

    @NotNull(message = "Task start time is required")
    @Column(name = "task_start_time", nullable = false)
    private LocalDateTime taskStartTime;

    @NotNull(message = "Task end time is required")
    @Column(name = "task_end_time", nullable = false)
    private LocalDateTime taskEndTime;
}
