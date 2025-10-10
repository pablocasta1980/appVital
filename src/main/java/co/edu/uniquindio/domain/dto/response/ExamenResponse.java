package co.edu.uniquindio.domain.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ExamenResponse {
    private String id;
    private String tipo;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaRealizacion;
    private LocalDateTime fechaResultado;
    private String laboratorio;
    private String medicoSolicitante;
    private String estado;
    private Map<String, String> resultados;
    private String archivo;
    private boolean urgencia;
    private String observaciones;
}