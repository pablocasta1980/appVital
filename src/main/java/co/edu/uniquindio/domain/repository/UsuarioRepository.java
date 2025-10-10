// src/main/java/co/edu/uniquindio/domain/repository/UsuarioRepository.java
package co.edu.uniquindio.domain.repository;

import co.edu.uniquindio.domain.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmailAndActivoTrue(String email);
}