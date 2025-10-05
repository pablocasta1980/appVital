package co.edu.uniquindio.repository;

import co.edu.uniquindio.models.documents.Medico;
import co.edu.uniquindio.models.enums.Especialidad;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepo extends MongoRepository<Medico, ObjectId> {

    // Métodos de búsqueda por campos únicos
    Optional<Medico> findByEmail(String email);
    Optional<Medico> findByIdentificacion(String identificacion);

    // Métodos de verificación de existencia
    Boolean existsByEmail(String email);
    Boolean existsByIdentificacion(String identificacion);

    // Métodos específicos de médico
    List<Medico> findByEspecialidad(Especialidad especialidad);
    List<Medico> findByEstado(Boolean estado);
    List<Medico> findByEspecialidadAndEstado(Especialidad especialidad, Boolean estado);

    // Método de eliminación
    void deleteByIdentificacion(String identificacion);
}