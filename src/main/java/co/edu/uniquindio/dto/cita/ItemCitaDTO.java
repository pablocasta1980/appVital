package co.edu.uniquindio.dto.cita;

import jakarta.validation.constraints.NotBlank;

public record ItemCitaDTO(
        @NotBlank String id,
        @NotBlank String medico,
        @NotBlank String paciente,
        @NotBlank String especialidad,
        @NotBlank String nroDocumento,
        @NotBlank String estado,
        @NotBlank String usuarioCreacion,
        @NotBlank String fechaCreacion,
        @NotBlank String horaCreacion) {
}
