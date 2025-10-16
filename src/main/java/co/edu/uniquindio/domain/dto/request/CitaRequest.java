package co.edu.uniquindio.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaRequest {

    @NotBlank(message = "El ID del médico es obligatorio")
    private String medicoId;

    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fecha;

    @NotBlank(message = "El tipo de cita es obligatorio")
    private String tipo; // PRESENCIAL, VIRTUAL

    private String motivo;
}