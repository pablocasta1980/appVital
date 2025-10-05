package co.edu.uniquindio.repository;

import co.edu.uniquindio.models.documents.Usuario;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepo extends MongoRepository<Usuario, ObjectId> {
    Optional<Usuario> findByEmail(String email);  // ← Cambiar a Optional
    Optional<Usuario> findByIdentificacion(String identificacion); // ← NUEVO MÉTODO
    void deleteByIdentificacion(String identificacion); // ← NUEVO MÉTODO
}
