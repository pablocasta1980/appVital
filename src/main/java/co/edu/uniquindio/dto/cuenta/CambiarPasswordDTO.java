package co.edu.uniquindio.dto.cuenta;

public record CambiarPasswordDTO(
        String email,
        String codigoVerificacion,
        String passwordNueva) {
}
