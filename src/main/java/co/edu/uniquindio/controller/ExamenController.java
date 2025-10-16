package co.edu.uniquindio.controller;

import co.edu.uniquindio.domain.dto.response.ExamenResponse;
import co.edu.uniquindio.service.interfaces.ExamenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Exámenes", description = "Endpoints para gestión de exámenes médicos")
public class ExamenController {

    private final ExamenService examenService;

    @Operation(summary = "Obtener exámenes del paciente", description = "Lista todos los exámenes de un paciente")
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ExamenResponse>> obtenerExamenesPorPaciente(
            @PathVariable String pacienteId) {
        List<ExamenResponse> examenes = examenService.obtenerExamenesPorPaciente(pacienteId);
        return ResponseEntity.ok(examenes);
    }

    @Operation(summary = "Obtener exámenes por estado", description = "Lista exámenes de un paciente por estado")
    @GetMapping("/paciente/{pacienteId}/estado/{estado}")
    public ResponseEntity<List<ExamenResponse>> obtenerExamenesPorPacienteYEstado(
            @PathVariable String pacienteId,
            @PathVariable String estado) {
        List<ExamenResponse> examenes = examenService.obtenerExamenesPorPacienteYEstado(pacienteId, estado);
        return ResponseEntity.ok(examenes);
    }

    @Operation(summary = "Obtener exámenes por tipo", description = "Lista exámenes de un paciente por tipo")
    @GetMapping("/paciente/{pacienteId}/tipo/{tipo}")
    public ResponseEntity<List<ExamenResponse>> obtenerExamenesPorPacienteYTipo(
            @PathVariable String pacienteId,
            @PathVariable String tipo) {
        List<ExamenResponse> examenes = examenService.obtenerExamenesPorPacienteYTipo(pacienteId, tipo);
        return ResponseEntity.ok(examenes);
    }

    @Operation(summary = "Obtener examen por ID", description = "Obtiene un examen específico del paciente")
    @GetMapping("/{examenId}/paciente/{pacienteId}")
    public ResponseEntity<ExamenResponse> obtenerExamenPorId(
            @PathVariable String examenId,
            @PathVariable String pacienteId) {
        ExamenResponse examen = examenService.obtenerExamenPorId(examenId, pacienteId);
        return ResponseEntity.ok(examen);
    }
}