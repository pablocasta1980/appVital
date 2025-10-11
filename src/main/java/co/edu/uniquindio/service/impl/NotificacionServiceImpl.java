package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.domain.dto.response.NotificacionResponse;
import co.edu.uniquindio.domain.model.Notificacion;
import co.edu.uniquindio.domain.repository.NotificacionRepository;
import co.edu.uniquindio.service.interfaces.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @Override
    public List<NotificacionResponse> obtenerNotificacionesPorPaciente(String pacienteId) {
        List<Notificacion> notificaciones = notificacionRepository.findByPacienteId(pacienteId);
        return notificaciones.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificacionResponse> obtenerNotificacionesNoLeidas(String pacienteId) {
        List<Notificacion> notificaciones = notificacionRepository.findByPacienteIdAndLeida(pacienteId, false);
        return notificaciones.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificacionResponse> obtenerNotificacionesPorTipo(String pacienteId, String tipo) {
        List<Notificacion> notificaciones = notificacionRepository.findByPacienteIdAndTipo(pacienteId, tipo);
        return notificaciones.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NotificacionResponse marcarComoLeida(String notificacionId, String pacienteId) {
        Notificacion notificacion = notificacionRepository.findById(notificacionId)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        if (!notificacion.getPacienteId().equals(pacienteId)) {
            throw new RuntimeException("No tienes permisos para esta notificación");
        }

        notificacion.setLeida(true);
        notificacion.setFechaLeida(LocalDateTime.now());

        Notificacion notificacionActualizada = notificacionRepository.save(notificacion);
        return convertirAResponse(notificacionActualizada);
    }

    @Override
    public void marcarTodasComoLeidas(String pacienteId) {
        List<Notificacion> notificacionesNoLeidas = notificacionRepository.findByPacienteIdAndLeida(pacienteId, false);

        notificacionesNoLeidas.forEach(notificacion -> {
            notificacion.setLeida(true);
            notificacion.setFechaLeida(LocalDateTime.now());
        });

        notificacionRepository.saveAll(notificacionesNoLeidas);
    }

    @Override
    public long contarNotificacionesNoLeidas(String pacienteId) {
        return notificacionRepository.countByPacienteIdAndLeidaFalse(pacienteId);
    }

    @Override
    public NotificacionResponse crearNotificacion(String pacienteId, String titulo, String mensaje, String tipo, boolean urgente) {
        Notificacion notificacion = new Notificacion();
        notificacion.setPacienteId(pacienteId);
        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);
        notificacion.setTipo(tipo);
        notificacion.setFechaCreacion(LocalDateTime.now());
        notificacion.setLeida(false);
        notificacion.setUrgente(urgente);

        Notificacion notificacionGuardada = notificacionRepository.save(notificacion);
        return convertirAResponse(notificacionGuardada);
    }

    private NotificacionResponse convertirAResponse(Notificacion notificacion) {
        NotificacionResponse response = new NotificacionResponse();
        response.setId(notificacion.getId());
        response.setTitulo(notificacion.getTitulo());
        response.setMensaje(notificacion.getMensaje());
        response.setTipo(notificacion.getTipo());
        response.setFechaCreacion(notificacion.getFechaCreacion());
        response.setFechaLeida(notificacion.getFechaLeida());
        response.setLeida(notificacion.isLeida());
        response.setUrgente(notificacion.isUrgente());
        response.setRelacionId(notificacion.getRelacionId());
        response.setRelacionTipo(notificacion.getRelacionTipo());
        return response;
    }

    @Override
    public NotificacionResponse marcarComoNoLeida(String notificacionId, String pacienteId) {
        Notificacion notificacion = notificacionRepository.findById(notificacionId)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));

        if (!notificacion.getPacienteId().equals(pacienteId)) {
            throw new RuntimeException("No tienes permisos para esta notificación");
        }

        notificacion.setLeida(false);
        notificacion.setFechaLeida(null);

        Notificacion notificacionActualizada = notificacionRepository.save(notificacion);
        return convertirAResponse(notificacionActualizada);
    }
}