package co.edu.uniquindio.domain.repository;

import co.edu.uniquindio.domain.model.Cita;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends MongoRepository<Cita, String> {

    // Buscar citas por paciente
    List<Cita> findByPacienteId(String pacienteId);

    // Buscar citas por paciente y estado
    List<Cita> findByPacienteIdAndEstado(String pacienteId, String estado);

    // Buscar citas por médico
    List<Cita> findByMedicoId(String medicoId);

    // Verificar si existe cita en misma fecha/hora
    boolean existsByMedicoIdAndFecha(String medicoId, LocalDateTime fecha);
}