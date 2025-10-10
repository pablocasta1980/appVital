package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.domain.dto.request.CitaRequest;
import co.edu.uniquindio.domain.dto.response.CitaResponse;
import java.util.List;

public interface CitaService {

    // Agendar nueva cita
    CitaResponse agendarCita(String pacienteId, CitaRequest citaRequest);

    // Cancelar cita
    CitaResponse cancelarCita(String citaId, String pacienteId);

    // Obtener citas por paciente
    List<CitaResponse> obtenerCitasPorPaciente(String pacienteId);

    // Obtener citas por paciente y estado
    List<CitaResponse> obtenerCitasPorPacienteYEstado(String pacienteId, String estado);
}