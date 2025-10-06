package co.edu.uniquindio.controllers;

import co.edu.uniquindio.dto.MensajeDTO;
import co.edu.uniquindio.dto.paciente.CrearPacienteDTO;
import co.edu.uniquindio.dto.paciente.EditarPacienteDTO;
import co.edu.uniquindio.dto.paciente.PacienteDTO;
import co.edu.uniquindio.servicios.interfaces.PacienteServicio;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteControlador {

    private final PacienteServicio pacienteServicio;

    @PostMapping
    public ResponseEntity<MensajeDTO<String>> crear(@Valid @RequestBody CrearPacienteDTO pacienteDTO) throws Exception {
        pacienteServicio.crear(pacienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MensajeDTO<>(false, "Paciente registrado exitosamente"));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth") // ← SOLO en endpoints protegidos
    public ResponseEntity<MensajeDTO<String>> editar(
            @PathVariable String id,
            @Valid @RequestBody EditarPacienteDTO pacienteDTO) throws Exception {
        pacienteServicio.editar(id, pacienteDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Paciente actualizado exitosamente"));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<MensajeDTO<String>> eliminar(@PathVariable String id) throws Exception {
        pacienteServicio.eliminar(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Paciente eliminado exitosamente"));
    }

    @DeleteMapping("/identificacion/{identificacion}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<MensajeDTO<String>> eliminarPorIdentificacion(
            @PathVariable String identificacion) throws Exception {
        pacienteServicio.eliminarPorIdentificacion(identificacion);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Paciente eliminado exitosamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<PacienteDTO>> obtener(@PathVariable String id) throws Exception {
        PacienteDTO paciente = pacienteServicio.obtener(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, paciente));
    }

    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<MensajeDTO<PacienteDTO>> obtenerPorIdentificacion(
            @PathVariable String identificacion) throws Exception {
        PacienteDTO paciente = pacienteServicio.obtenerPorIdentificacion(identificacion);
        return ResponseEntity.ok(new MensajeDTO<>(false, paciente));
    }
}