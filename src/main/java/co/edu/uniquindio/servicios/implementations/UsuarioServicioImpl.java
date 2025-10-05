package co.edu.uniquindio.servicios.implementations;

import co.edu.uniquindio.dto.CrearUsuarioDTO;
import co.edu.uniquindio.dto.EditarUsuarioDTO;
import co.edu.uniquindio.dto.UsuarioDTO;
import co.edu.uniquindio.mappers.UsuarioMapper;
import co.edu.uniquindio.models.documents.Usuario;
import co.edu.uniquindio.repository.UsuarioRepo;
import co.edu.uniquindio.servicios.interfaces.UsuarioServicio;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServicioImpl implements UsuarioServicio {
    private final UsuarioRepo usuarioRepo;
    private final UsuarioMapper usuarioMapper;
    private final MongoTemplate mongoTemplate;


    @Override
    public void crear(CrearUsuarioDTO crearUsuarioDTO) throws Exception {
        // Validar que el email no esté repetido
        if(existeEmail(crearUsuarioDTO.email())) {
            throw new Exception("El email ya está registrado");
        }

        Usuario usuario = usuarioMapper.toDocument(crearUsuarioDTO);
        // Se codifica la contraseña (si implementas seguridad)
        // usuario.setPassword(passwordEncoder.encode(crearUsuarioDTO.password()));
        usuarioRepo.save(usuario);
    }

    private boolean existeEmail(@NotBlank @Length(max = 50) @Email String email) {
        return usuarioRepo.findByEmail(email).isPresent();
    }

    @Override
    public void editar(String id, EditarUsuarioDTO editarUsuarioDTO) throws Exception {


                // 1. Convertir String id a ObjectId y buscar usuario
        ObjectId objectId = new ObjectId(id);
        Usuario usuario = usuarioRepo.findById(objectId)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // 2. Verificar si el nuevo email ya existe (si cambió el email)
        if (!usuario.getEmail().equals(editarUsuarioDTO.email())) {
            if (existeEmail(editarUsuarioDTO.email())) {
                throw new Exception("El email ya está registrado por otro usuario");
            }
        }

        // 3. Actualizar campos usando el mapper
        usuarioMapper.toDocument(editarUsuarioDTO, usuario);

        // 4. Guardar cambios
        usuarioRepo.save(usuario);
    }

    @Override
    public void eliminar(String id) throws Exception {
        try {
            // 1. Convertir String id a ObjectId
            ObjectId objectId = new ObjectId(id);

            // 2. Verificar que el usuario existe
            if (!usuarioRepo.existsById(objectId)) {
                throw new Exception("Usuario no encontrado");
            }

            // 3. Eliminar el usuario
            usuarioRepo.deleteById(objectId);

        } catch (IllegalArgumentException e) {
            throw new Exception("ID con formato inválido");
        }
    }

    @Override
    public UsuarioDTO obtener(String id) throws Exception {
        return null;
    }

    @Override
    public void eliminarPorIdentificacion(String identificacion) throws Exception {
        // 1. Buscar usuario por identificación
        Usuario usuario = usuarioRepo.findByIdentificacion(identificacion)
                .orElseThrow(() -> new Exception("Usuario con identificación " + identificacion + " no encontrado"));

        // 2. Eliminar el usuario
        usuarioRepo.delete(usuario);

        // Alternativa directa (si prefieres):
        // usuarioRepo.deleteByIdentificacion(identificacion);
    }

    @Override
    public UsuarioDTO obtenerPorIdentificacion(String identificacion) throws Exception {
        // 1. Buscar usuario por identificación
        Usuario usuario = usuarioRepo.findByIdentificacion(identificacion)
                .orElseThrow(() -> new Exception("Usuario con identificación " + identificacion + " no encontrado"));

        // 2. Convertir a DTO usando el mapper
        return usuarioMapper.toDTO(usuario);
    }


    //TODO implementar todos los métodos de la interfaz
}
