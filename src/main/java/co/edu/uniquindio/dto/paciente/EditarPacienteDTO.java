package co.edu.uniquindio.dto.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record EditarPacienteDTO(
        // Campos editables
        @NotBlank @Length(max = 100) String nombre,
        @Length(max = 10) String telefono,
        @NotBlank @Length(max = 100) String ciudad,
        @NotBlank @Length(max = 100) String direccion,
        @NotBlank @Length(max = 50) @Email String email,

        // Campos médicos editables
        LocalDate fechaNacimiento,
        String alergias,
        String eps,
        String tipoSangre,
        Double peso,
        Double altura,
        String contactoEmergencia,
        String telefonoEmergencia
) {
}