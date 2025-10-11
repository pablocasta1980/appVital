package co.edu.uniquindio.domain.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificacionResponse {
    private String id;
    private String titulo;
    private String mensaje;
    private String tipo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaLeida;
    private boolean leida;
    private boolean urgente;
    private String relacionId;
    private String relacionTipo;
}