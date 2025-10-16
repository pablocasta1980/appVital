package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.domain.dto.response.ExamenResponse;
import co.edu.uniquindio.domain.model.Examen;
import co.edu.uniquindio.domain.repository.ExamenRepository;
import co.edu.uniquindio.service.interfaces.ExamenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamenServiceImpl implements ExamenService {

    private final ExamenRepository examenRepository;

    @Override
    public List<ExamenResponse> obtenerExamenesPorPaciente(String pacienteId) {
        List<Examen> examenes = examenRepository.findByPacienteId(pacienteId);
        return examenes.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExamenResponse> obtenerExamenesPorPacienteYEstado(String pacienteId, String estado) {
        List<Examen> examenes = examenRepository.findByPacienteIdAndEstado(pacienteId, estado);
        return examenes.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExamenResponse> obtenerExamenesPorPacienteYTipo(String pacienteId, String tipo) {
        List<Examen> examenes = examenRepository.findByPacienteIdAndTipo(pacienteId, tipo);
        return examenes.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ExamenResponse obtenerExamenPorId(String examenId, String pacienteId) {
        Examen examen = examenRepository.findById(examenId)
                .orElseThrow(() -> new RuntimeException("Examen no encontrado"));

        if (!examen.getPacienteId().equals(pacienteId)) {
            throw new RuntimeException("No tienes permisos para ver este examen");
        }

        return convertirAResponse(examen);
    }

    private ExamenResponse convertirAResponse(Examen examen) {
        ExamenResponse response = new ExamenResponse();
        response.setId(examen.getId());
        response.setTipo(examen.getTipo());
        response.setFechaSolicitud(examen.getFechaSolicitud());
        response.setFechaRealizacion(examen.getFechaRealizacion());
        response.setFechaResultado(examen.getFechaResultado());
        response.setLaboratorio(examen.getLaboratorio());
        response.setMedicoSolicitante(examen.getMedicoSolicitante());
        response.setEstado(examen.getEstado());
        response.setResultados(examen.getResultados());
        response.setArchivo(examen.getArchivo());
        response.setUrgencia(examen.isUrgencia());
        response.setObservaciones(examen.getObservaciones());
        return response;
    }
}