package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.domain.dto.request.RegistroPacienteRequest;
import co.edu.uniquindio.domain.dto.response.UsuarioResponse;

public interface UsuarioService {

    // Registrar nuevo paciente (por defecto)
    UsuarioResponse registrarPaciente(RegistroPacienteRequest request);

    // Verificar si email existe (para validación)
    boolean existeEmail(String email);
}