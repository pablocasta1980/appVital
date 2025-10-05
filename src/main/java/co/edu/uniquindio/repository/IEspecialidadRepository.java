package co.edu.uniquindio.repository;

import co.edu.uniquindio.models.documents.Especialidad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEspecialidadRepository extends MongoRepository<Especialidad, String> {

}
