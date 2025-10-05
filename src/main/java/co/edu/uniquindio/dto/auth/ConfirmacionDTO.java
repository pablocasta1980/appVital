package co.edu.uniquindio.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record ConfirmacionDTO(
        @NotBlank String token
) {
}