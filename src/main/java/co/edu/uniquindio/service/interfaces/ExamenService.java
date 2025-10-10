package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.domain.dto.response.ExamenResponse;
import java.util.List;

public interface ExamenService {

    // Obtener exámenes por paciente
    List<ExamenResponse> obtenerExamenesPorPaciente(String pacienteId);

    // Obtener exámenes por paciente y estado
    List<ExamenResponse> obtenerExamenesPorPacienteYEstado(String pacienteId, String estado);

    // Obtener exámenes por paciente y tipo
    List<ExamenResponse> obtenerExamenesPorPacienteYTipo(String pacienteId, String tipo);

    // Obtener examen por ID (solo si pertenece al paciente)
    ExamenResponse obtenerExamenPorId(String examenId, String pacienteId);
}