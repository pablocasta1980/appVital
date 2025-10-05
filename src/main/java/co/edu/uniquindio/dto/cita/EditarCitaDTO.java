package co.edu.uniquindio.dto.cita;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EditarCitaDTO(
        @NotBlank String id,
        @NotBlank String idMedico,
        @NotBlank String idPaciente,
        @NotBlank String idSede,
        @NotNull boolean confirmado,
        @Future(message = "La cita médica no puede ser aginada en una fecha anterior a la actual.")
        LocalDateTime fechaCita,
        @NotBlank String consultorio,
        @NotBlank String comentarios,
        @NotBlank String estado,
        @NotBlank String estadoRegistro,
        @NotBlank String usuarioCreacion) {
}
