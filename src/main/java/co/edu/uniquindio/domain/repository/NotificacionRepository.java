package co.edu.uniquindio.domain.repository;

import co.edu.uniquindio.domain.model.Notificacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends MongoRepository<Notificacion, String> {

    // Buscar notificaciones por paciente
    List<Notificacion> findByPacienteId(String pacienteId);

    // Buscar notificaciones por paciente y estado de lectura
    List<Notificacion> findByPacienteIdAndLeida(String pacienteId, boolean leida);

    // Buscar notificaciones por paciente y tipo
    List<Notificacion> findByPacienteIdAndTipo(String pacienteId, String tipo);

    // Contar notificaciones no leídas por paciente
    long countByPacienteIdAndLeidaFalse(String pacienteId);

    // Buscar notificaciones urgentes no leídas
    List<Notificacion> findByPacienteIdAndUrgenteTrueAndLeidaFalse(String pacienteId);
}