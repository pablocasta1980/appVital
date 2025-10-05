package co.edu.uniquindio.dto.cuenta;

import jakarta.validation.constraints.NotNull;

public record TokenDTO(
        @NotNull String token) {
}
