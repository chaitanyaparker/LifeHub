package com.example.LifeHub.Repository;

import com.example.LifeHub.Entity.Tasks;
import com.example.LifeHub.Enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Tasks, Long> {

    List<Tasks> findByUserUserId(Long userId);

    long countByUserUserId(Long userId);

    long countByUserUserIdAndTaskStatus(Long userId, TaskStatus taskStatus);

    long countByUserUserIdAndTaskStatusNotAndTaskEndTimeBefore(
            Long userId,
            TaskStatus taskStatus,
            LocalDateTime taskEndTime
    );
}