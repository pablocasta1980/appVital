package co.edu.uniquindio.controller;

import co.edu.uniquindio.domain.dto.response.NotificacionResponse;
import co.edu.uniquindio.service.interfaces.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Notificaciones", description = "Endpoints para gestión de notificaciones de salud")
public class NotificacionController {

    private final NotificacionService notificacionService;

    @Operation(summary = "Obtener notificaciones del paciente", description = "Lista todas las notificaciones de un paciente")
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<NotificacionResponse>> obtenerNotificacionesPorPaciente(
            @PathVariable String pacienteId) {
        List<NotificacionResponse> notificaciones = notificacionService.obtenerNotificacionesPorPaciente(pacienteId);
        return ResponseEntity.ok(notificaciones);
    }

    @Operation(summary = "Obtener notificaciones no leídas", description = "Lista notificaciones no leídas del paciente")
    @GetMapping("/paciente/{pacienteId}/no-leidas")
    public ResponseEntity<List<NotificacionResponse>> obtenerNotificacionesNoLeidas(
            @PathVariable String pacienteId) {
        List<NotificacionResponse> notificaciones = notificacionService.obtenerNotificacionesNoLeidas(pacienteId);
        return ResponseEntity.ok(notificaciones);
    }

    @Operation(summary = "Obtener notificaciones por tipo", description = "Lista notificaciones del paciente por tipo")
    @GetMapping("/paciente/{pacienteId}/tipo/{tipo}")
    public ResponseEntity<List<NotificacionResponse>> obtenerNotificacionesPorTipo(
            @PathVariable String pacienteId,
            @PathVariable String tipo) {
        List<NotificacionResponse> notificaciones = notificacionService.obtenerNotificacionesPorTipo(pacienteId, tipo);
        return ResponseEntity.ok(notificaciones);
    }

    @Operation(summary = "Marcar notificación como leída", description = "Marca una notificación específica como leída")
    @PutMapping("/{notificacionId}/marcar-leida/paciente/{pacienteId}")
    public ResponseEntity<NotificacionResponse> marcarComoLeida(
            @PathVariable String notificacionId,
            @PathVariable String pacienteId) {
        NotificacionResponse notificacion = notificacionService.marcarComoLeida(notificacionId, pacienteId);
        return ResponseEntity.ok(notificacion);
    }

    @Operation(summary = "Marcar todas como leídas", description = "Marca todas las notificaciones del paciente como leídas")
    @PutMapping("/marcar-todas-leidas/paciente/{pacienteId}")
    public ResponseEntity<Void> marcarTodasComoLeidas(@PathVariable String pacienteId) {
        notificacionService.marcarTodasComoLeidas(pacienteId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Contar notificaciones no leídas", description = "Obtiene el número de notificaciones no leídas")
    @GetMapping("/contar-no-leidas/paciente/{pacienteId}")
    public ResponseEntity<Long> contarNotificacionesNoLeidas(@PathVariable String pacienteId) {
        long count = notificacionService.contarNotificacionesNoLeidas(pacienteId);
        return ResponseEntity.ok(count);
    }

    @Operation(summary = "Marcar notificación como no leída", description = "Marca una notificación específica como no leída")
    @PutMapping("/{notificacionId}/marcar-no-leida/paciente/{pacienteId}")
    public ResponseEntity<NotificacionResponse> marcarComoNoLeida(
            @PathVariable String notificacionId,
            @PathVariable String pacienteId) {
        NotificacionResponse notificacion = notificacionService.marcarComoNoLeida(notificacionId, pacienteId);
        return ResponseEntity.ok(notificacion);
    }
}