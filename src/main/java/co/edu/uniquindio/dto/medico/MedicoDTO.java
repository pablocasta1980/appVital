package co.edu.uniquindio.dto.medico;

import co.edu.uniquindio.models.enums.Especialidad;

import java.time.LocalTime;

public record MedicoDTO(
        String id,
        String nombre,
        String identificacion,
        String telefono,
        String ciudad,
        String direccion,
        String email,

        // Campos específicos
        Especialidad especialidad,
        String registroMedico,
        LocalTime horaInicio,
        LocalTime horaFin,
        Integer añosExperiencia,
        Double calificacion,
        Boolean estado,
        String consultorio
) {
}