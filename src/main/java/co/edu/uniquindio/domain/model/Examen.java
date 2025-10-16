package co.edu.uniquindio.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "examenes")
public class Examen {
    @Id
    private String id;

    private String pacienteId;
    private String tipo;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaRealizacion;
    private LocalDateTime fechaResultado;
    private String laboratorio;
    private String medicoSolicitante;
    private String estado; // PENDIENTE, EN_PROCESO, COMPLETADO, CANCELADO
    private Map<String, String> resultados;
    private String archivo;
    private boolean urgencia;
    private String observaciones;
}