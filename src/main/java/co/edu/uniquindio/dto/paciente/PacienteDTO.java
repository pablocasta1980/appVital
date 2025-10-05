package co.edu.uniquindio.dto.paciente;

import java.time.LocalDate;

public record PacienteDTO(
        String id,
        String nombre,
        String identificacion,
        String telefono,
        String ciudad,
        String direccion,
        String email,

        // Campos específicos
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