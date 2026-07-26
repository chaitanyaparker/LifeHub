package com.example.LifeHub.Entity;

import com.example.LifeHub.Enums.TaskCategory;
import com.example.LifeHub.Enums.TaskPriority;
import com.example.LifeHub.Enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String taskName;

    @Enumerated(EnumType.STRING)
    private TaskCategory taskCategory;

    @Enumerated(EnumType.STRING)
    private TaskPriority taskPriority;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    private String taskDescription;

    private LocalDateTime taskStartTime;

    private LocalDateTime taskEndTime;
}
