package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.domain.dto.response.NotificacionResponse;
import java.util.List;

public interface NotificacionService {

    // Obtener notificaciones por paciente
    List<NotificacionResponse> obtenerNotificacionesPorPaciente(String pacienteId);

    // Obtener notificaciones no leídas
    List<NotificacionResponse> obtenerNotificacionesNoLeidas(String pacienteId);

    // Obtener notificaciones por tipo
    List<NotificacionResponse> obtenerNotificacionesPorTipo(String pacienteId, String tipo);

    // Marcar notificación como leída
    NotificacionResponse marcarComoLeida(String notificacionId, String pacienteId);

    // Marcar todas como leídas
    void marcarTodasComoLeidas(String pacienteId);

    // Contar notificaciones no leídas
    long contarNotificacionesNoLeidas(String pacienteId);

    // Marcar notificación como no leída
    NotificacionResponse marcarComoNoLeida(String notificacionId, String pacienteId);

    // Crear notificación (para uso interno del sistema)
    NotificacionResponse crearNotificacion(String pacienteId, String titulo, String mensaje, String tipo, boolean urgente);
}