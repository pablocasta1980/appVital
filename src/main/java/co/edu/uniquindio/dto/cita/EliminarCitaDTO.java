package co.edu.uniquindio.dto.cita;

import jakarta.validation.constraints.NotBlank;

public record EliminarCitaDTO(
        @NotBlank String id,
        @NotBlank String estadoRegistro) {
}
