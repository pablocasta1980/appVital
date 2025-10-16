package co.edu.uniquindio.domain.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaResponse {
    private String id;
    private String medicoNombre;
    private String especialidad;
    private LocalDateTime fecha;
    private String estado;
    private String tipo;
    private String consultorio;
    private String motivo;
}