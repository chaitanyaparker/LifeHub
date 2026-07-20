package com.example.LifeHub.Entity;

import com.example.LifeHub.Enums.Role;
import com.example.LifeHub.Enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String profilePicture;

    private String bio;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isEmailVerified;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreatedBy
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}


