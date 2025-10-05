package co.edu.uniquindio.dto.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record CrearPacienteDTO(
        // Campos básicos
        @NotBlank @Length(max = 100) String nombre,
        @NotBlank String identificacion,
        @Length(max = 10) String telefono,
        @NotBlank @Length(max = 100) String ciudad,
        @NotBlank @Length(max = 100) String direccion,
        @NotBlank @Length(max = 50) @Email String email,
        @NotBlank @Length(min = 7, max = 20) String password,

        // Campos específicos de paciente
        @NotNull LocalDate fechaNacimiento,
        String alergias,
        @NotBlank String eps,
        String tipoSangre,
        Double peso,
        Double altura,
        @NotBlank String contactoEmergencia,
        @NotBlank String telefonoEmergencia
) {
}