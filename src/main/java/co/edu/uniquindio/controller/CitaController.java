package co.edu.uniquindio.controller;

import co.edu.uniquindio.domain.dto.request.CitaRequest;
import co.edu.uniquindio.domain.dto.response.CitaResponse;
import co.edu.uniquindio.service.interfaces.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Citas", description = "Endpoints para gestión de citas médicas")
public class CitaController {

    private final CitaService citaService;

    @Operation(summary = "Agendar nueva cita", description = "Crea una nueva cita médica para el paciente")
    @PostMapping
    public ResponseEntity<CitaResponse> agendarCita(
            @RequestHeader String pacienteId,
            @Valid @RequestBody CitaRequest citaRequest) {
        CitaResponse response = citaService.agendarCita(pacienteId, citaRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cancelar cita", description = "Cancela una cita existente")
    @PutMapping("/{citaId}/cancelar")
    public ResponseEntity<CitaResponse> cancelarCita(
            @PathVariable String citaId,
            @RequestHeader String pacienteId) {
        CitaResponse response = citaService.cancelarCita(citaId, pacienteId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener citas del paciente", description = "Lista todas las citas de un paciente")
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<CitaResponse>> obtenerCitasPorPaciente(
            @PathVariable String pacienteId) {
        List<CitaResponse> citas = citaService.obtenerCitasPorPaciente(pacienteId);
        return ResponseEntity.ok(citas);
    }

    @Operation(summary = "Obtener citas por estado", description = "Lista citas de un paciente por estado")
    @GetMapping("/paciente/{pacienteId}/estado/{estado}")
    public ResponseEntity<List<CitaResponse>> obtenerCitasPorPacienteYEstado(
            @PathVariable String pacienteId,
            @PathVariable String estado) {
        List<CitaResponse> citas = citaService.obtenerCitasPorPacienteYEstado(pacienteId, estado);
        return ResponseEntity.ok(citas);
    }
}