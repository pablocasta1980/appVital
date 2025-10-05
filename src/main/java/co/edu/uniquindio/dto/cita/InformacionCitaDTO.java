package co.edu.uniquindio.dto.cita;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InformacionCitaDTO(
        @NotBlank String id,
        @NotBlank String codigo,
        @NotBlank String paciente,
        @NotBlank String tipoDocumento,
        @NotBlank String nroDocumento,
        @NotBlank String celularPaciente,
        @NotBlank String sede,
        @NotNull boolean confirmada,
        @NotBlank String fechaCita,
        @NotBlank String horaCita,
        @NotBlank String especialidad,
        @NotBlank String duracion,
        @NotBlank String consultorio,
        @NotBlank String comentarios,
        @NotBlank String estado,
        @NotBlank String estadoRegistro,
        @NotBlank String usuarioCreacion,
        @NotBlank String fechaCreacion,
        @NotBlank String horaCreacion) {
}
