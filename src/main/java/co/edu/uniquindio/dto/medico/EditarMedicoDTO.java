package co.edu.uniquindio.dto.medico;

import co.edu.uniquindio.models.enums.Especialidad;
import co.edu.uniquindio.models.enums.EstadoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.LocalTime;

public record EditarMedicoDTO(
        // Campos editables
        @NotBlank @Length(max = 100) String nombre,
        @Length(max = 10) String telefono,
        @NotBlank @Length(max = 100) String ciudad,
        @NotBlank @Length(max = 100) String direccion,
        @NotBlank @Length(max = 50) @Email String email,

        // Campos profesionales editables
        Especialidad especialidad,
        LocalTime horaInicio,
        LocalTime horaFin,
        Integer añosExperiencia,
        String consultorio,
        EstadoUsuario estado, // ← CAMBIAR de Boolean a EstadoUsuario
        Boolean estadoProfesional
) {
}