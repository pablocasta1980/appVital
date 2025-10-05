package co.edu.uniquindio.dto.cita;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CrearCitaDTO(
        @NotBlank String idMedico,
        @NotBlank String idPaciente,
        @NotBlank String idSede,
        @Future(message = "La cita médica no puede ser asignada en una fecha anterior a la actual.")
        LocalDateTime fechaCita,
        @NotBlank String especialidad,
        @NotBlank String duracion,
        @NotBlank String consultorio,
        @NotBlank String comentarios,
        @NotBlank String usuarioCreacion,
        LocalDateTime fechaCreacion) {
}
