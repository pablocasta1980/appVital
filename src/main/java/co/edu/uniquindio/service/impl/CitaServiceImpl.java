package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.domain.dto.request.CitaRequest;
import co.edu.uniquindio.domain.dto.response.CitaResponse;
import co.edu.uniquindio.domain.model.Cita;
import co.edu.uniquindio.domain.repository.CitaRepository;
import co.edu.uniquindio.service.interfaces.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    @Override
    public CitaResponse agendarCita(String pacienteId, CitaRequest citaRequest) {
       
        Cita cita = new Cita();
        cita.setPacienteId(pacienteId);
        cita.setMedicoId(citaRequest.getMedicoId());
        cita.setMedicoNombre("Médico por asignar"); // Temporal
        cita.setEspecialidad("Especialidad por asignar"); // Temporal
        cita.setFecha(citaRequest.getFecha());
        cita.setEstado("PENDIENTE");
        cita.setTipo(citaRequest.getTipo());
        cita.setMotivo(citaRequest.getMotivo());
        cita.setConsultorio(citaRequest.getTipo().equals("PRESENCIAL") ? "Por asignar" : "Plataforma Online");
        cita.setFechaCreacion(LocalDateTime.now());
        cita.setFechaActualizacion(LocalDateTime.now());

        Cita citaGuardada = citaRepository.save(cita);
        return convertirAResponse(citaGuardada);
    }

    @Override
    public CitaResponse cancelarCita(String citaId, String pacienteId) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        if (!cita.getPacienteId().equals(pacienteId)) {
            throw new RuntimeException("No tienes permisos para cancelar esta cita");
        }

        cita.setEstado("CANCELADA");
        cita.setFechaActualizacion(LocalDateTime.now());

        Cita citaActualizada = citaRepository.save(cita);
        return convertirAResponse(citaActualizada);
    }

    @Override
    public List<CitaResponse> obtenerCitasPorPaciente(String pacienteId) {
        List<Cita> citas = citaRepository.findByPacienteId(pacienteId);
        return citas.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CitaResponse> obtenerCitasPorPacienteYEstado(String pacienteId, String estado) {
        List<Cita> citas = citaRepository.findByPacienteIdAndEstado(pacienteId, estado);
        return citas.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    private CitaResponse convertirAResponse(Cita cita) {
        CitaResponse response = new CitaResponse();
        response.setId(cita.getId());
        response.setMedicoNombre(cita.getMedicoNombre());
        response.setEspecialidad(cita.getEspecialidad());
        response.setFecha(cita.getFecha());
        response.setEstado(cita.getEstado());
        response.setTipo(cita.getTipo());
        response.setConsultorio(cita.getConsultorio());
        response.setMotivo(cita.getMotivo());
        return response;
    }
}