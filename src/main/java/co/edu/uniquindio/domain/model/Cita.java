package co.edu.uniquindio.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "citas")
public class Cita {
    @Id
    private String id;

    private String pacienteId;      // ID del paciente
    private String medicoId;        // ID del médico
    private String medicoNombre;    // Nombre del médico
    private String especialidad;    // Especialidad del médico

    private LocalDateTime fecha;    // Fecha y hora de la cita
    private String estado;          // PENDIENTE, CONFIRMADA, COMPLETADA, CANCELADA
    private String tipo;            // PRESENCIAL, VIRTUAL
    private String motivo;          // Motivo de la consulta
    private String consultorio;     // Consultorio asignado

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}
