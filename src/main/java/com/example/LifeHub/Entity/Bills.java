
package com.example.LifeHub.Entity;

import com.example.LifeHub.Enums.BillsStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "bills",
        indexes = {
                @Index(
                        name = "idx_bills_user_id",
                        columnList = "user_id"
                ),
                @Index(
                        name = "idx_bills_user_status",
                        columnList = "user_id, bills_status"
                ),
                @Index(
                        name = "idx_bills_user_due_date",
                        columnList = "user_id, due_date"
                )
        }
)
@Getter
@Setter
@ToString(exclude = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billsId;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String title;

    @NotNull
    @Column(nullable = false)
    private Instant dueDate;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "bills_status", nullable = false, length = 30)
    private BillsStatus billsStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;
}
