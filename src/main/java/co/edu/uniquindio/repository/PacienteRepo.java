package co.edu.uniquindio.repository;

import co.edu.uniquindio.models.documents.Paciente;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepo extends MongoRepository<Paciente, ObjectId> {

    // Métodos de búsqueda por campos únicos
    Optional<Paciente> findByEmail(String email);
    Optional<Paciente> findByIdentificacion(String identificacion);

    // Métodos de verificación de existencia
    Boolean existsByEmail(String email);
    Boolean existsByIdentificacion(String identificacion);

    // Método de eliminación
    void deleteByIdentificacion(String identificacion);
}