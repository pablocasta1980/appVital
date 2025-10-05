package co.edu.uniquindio.dto.medico;

import co.edu.uniquindio.models.enums.Especialidad;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalTime;

public record CrearMedicoDTO(
        // Campos básicos
        @NotBlank @Length(max = 100) String nombre,
        @NotBlank String identificacion,
        @Length(max = 10) String telefono,
        @NotBlank @Length(max = 100) String ciudad,
        @NotBlank @Length(max = 100) String direccion,
        @NotBlank @Length(max = 50) @Email String email,
        @NotBlank @Length(min = 7, max = 20) String password,

        // Campos específicos de médico
        @NotNull Especialidad especialidad,
        @NotBlank String registroMedico,
        @NotNull LocalTime horaInicio,
        @NotNull LocalTime horaFin,
        @NotNull Integer añosExperiencia,
        String consultorio
) {
}