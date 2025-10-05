package co.edu.uniquindio.dto.email;

import jakarta.validation.constraints.NotBlank;

public record EmailDTO(
        @NotBlank String destinatario,
        @NotBlank String asunto,
        @NotBlank String cuerpo) {
}
