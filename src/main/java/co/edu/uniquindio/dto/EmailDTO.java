package co.edu.uniquindio.dto;

public record EmailDTO(
        String asunto,
        String cuerpo,
        String destinatario
) {
}
