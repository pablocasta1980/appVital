package co.edu.uniquindio.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "notificaciones")
public class Notificacion {
    @Id
    private String id;

    private String pacienteId;
    private String titulo;
    private String mensaje;
    private String tipo; // RECORDATORIO, ALERTA, RESULTADO, INFORMACION
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaLeida;
    private boolean leida;
    private boolean urgente;
    private String relacionId; // ID de cita, examen, etc.
    private String relacionTipo; // CITA, EXAMEN, MEDICAMENTO
}