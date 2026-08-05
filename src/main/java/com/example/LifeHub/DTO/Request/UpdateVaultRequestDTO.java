package com.example.LifeHub.DTO.Request;

import com.example.LifeHub.Enums.VaultCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateVaultRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @URL(message = "Invalid website URL")
    private String websiteUrl;

    @NotBlank(message = "loginIdentifier is required")
    private String loginIdentifier;

    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must contain at least 4 characters")
    private String password;

    @NotNull(message = "Category is required")
    private VaultCategory category;

    private boolean favorite;
}
