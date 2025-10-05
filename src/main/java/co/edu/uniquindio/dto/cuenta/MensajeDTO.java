package co.edu.uniquindio.dto.cuenta;

public record MensajeDTO<T>(
        boolean error,
        T respuesta) {
}