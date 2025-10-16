package co.edu.uniquindio.domain.repository;

import co.edu.uniquindio.domain.model.Examen;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExamenRepository extends MongoRepository<Examen, String> {

    // Buscar exámenes por paciente
    List<Examen> findByPacienteId(String pacienteId);

    // Buscar exámenes por paciente y estado
    List<Examen> findByPacienteIdAndEstado(String pacienteId, String estado);

    // Buscar exámenes por tipo
    List<Examen> findByPacienteIdAndTipo(String pacienteId, String tipo);

    // Verificar si existe examen por paciente y tipo en fecha
    boolean existsByPacienteIdAndTipoAndFechaSolicitudBetween(
            String pacienteId, String tipo, LocalDateTime start, LocalDateTime end);
}