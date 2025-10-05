package co.edu.uniquindio.repository;

import co.edu.uniquindio.models.documents.Sede;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISedeRepository extends MongoRepository<Sede, String> {

}
