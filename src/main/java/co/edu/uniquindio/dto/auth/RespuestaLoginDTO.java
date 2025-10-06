package co.edu.uniquindio.dto.auth;

import co.edu.uniquindio.models.enums.RolUsuario;

public record RespuestaLoginDTO(
        String token,
        String id,
        String nombre,
        String email,
        RolUsuario rol,
        boolean cuentaActiva,
        String tipoUsuario // "PACIENTE" o "MÉDICO"
) {}