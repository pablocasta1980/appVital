package co.edu.uniquindio.controllers;

import co.edu.uniquindio.dto.MensajeDTO;
import co.edu.uniquindio.dto.medico.CrearMedicoDTO;
import co.edu.uniquindio.dto.medico.EditarMedicoDTO;
import co.edu.uniquindio.dto.medico.MedicoDTO;
import co.edu.uniquindio.models.enums.Especialidad;
import co.edu.uniquindio.servicios.interfaces.MedicoServicio;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
@SecurityRequirement(name = "bearerAuth") // ← AGREGAR ESTA LÍNEA
@RequiredArgsConstructor
public class MedicoControlador {

    private final MedicoServicio medicoServicio;

    @PostMapping
    public ResponseEntity<MensajeDTO<String>> crear(@Valid @RequestBody CrearMedicoDTO medicoDTO) throws Exception {
        medicoServicio.crear(medicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MensajeDTO<>(false, "Médico registrado exitosamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> editar(
            @PathVariable String id,
            @Valid @RequestBody EditarMedicoDTO medicoDTO) throws Exception {
        medicoServicio.editar(id, medicoDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Médico actualizado exitosamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminar(@PathVariable String id) throws Exception {
        medicoServicio.eliminar(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Médico eliminado exitosamente"));
    }

    @DeleteMapping("/identificacion/{identificacion}")
    public ResponseEntity<MensajeDTO<String>> eliminarPorIdentificacion(
            @PathVariable String identificacion) throws Exception {
        medicoServicio.eliminarPorIdentificacion(identificacion);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Médico eliminado exitosamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<MedicoDTO>> obtener(@PathVariable String id) throws Exception {
        MedicoDTO medico = medicoServicio.obtener(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, medico));
    }

    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<MensajeDTO<MedicoDTO>> obtenerPorIdentificacion(
            @PathVariable String identificacion) throws Exception {
        MedicoDTO medico = medicoServicio.obtenerPorIdentificacion(identificacion);
        return ResponseEntity.ok(new MensajeDTO<>(false, medico));
    }

    @GetMapping
    public ResponseEntity<MensajeDTO<List<MedicoDTO>>> listarTodos() throws Exception {
        List<MedicoDTO> medicos = medicoServicio.listarTodos();
        return ResponseEntity.ok(new MensajeDTO<>(false, medicos));
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<MensajeDTO<List<MedicoDTO>>> listarPorEspecialidad(
            @PathVariable Especialidad especialidad) throws Exception {
        List<MedicoDTO> medicos = medicoServicio.listarPorEspecialidad(especialidad);
        return ResponseEntity.ok(new MensajeDTO<>(false, medicos));
    }

    @GetMapping("/activos")
    public ResponseEntity<MensajeDTO<List<MedicoDTO>>> listarActivos() throws Exception {
        List<MedicoDTO> medicos = medicoServicio.listarActivos();
        return ResponseEntity.ok(new MensajeDTO<>(false, medicos));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<MensajeDTO<String>> cambiarEstado(
            @PathVariable String id,
            @RequestParam Boolean estado) throws Exception {
        medicoServicio.cambiarEstado(id, estado);
        String mensaje = estado ? "Médico activado" : "Médico desactivado";
        return ResponseEntity.ok(new MensajeDTO<>(false, mensaje + " exitosamente"));
    }
}