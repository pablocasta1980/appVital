package co.edu.uniquindio.mappers;

import co.edu.uniquindio.dto.CrearUsuarioDTO;
import co.edu.uniquindio.dto.EditarUsuarioDTO;
import co.edu.uniquindio.dto.UsuarioDTO;
import co.edu.uniquindio.models.documents.Usuario;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toDocument(CrearUsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setIdentificacion(dto.identificacion()); // ← NUEVO
        usuario.setTelefono(dto.telefono());
        usuario.setCiudad(dto.ciudad());
        usuario.setDireccion(dto.direccion());
        usuario.setEmail(dto.email());
        usuario.setPassword(dto.password());
        return usuario;
    }

    public UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getNombre(),
                usuario.getIdentificacion(), // ← NUEVO
                usuario.getTelefono(),
                usuario.getCiudad(),
                usuario.getDireccion(),
                usuario.getEmail()
        );
    }

    public void toDocument(EditarUsuarioDTO dto, Usuario usuario) {
        usuario.setNombre(dto.nombre());
        usuario.setIdentificacion(dto.identificacion()); // ← NUEVO
        usuario.setTelefono(dto.telefono());
        usuario.setCiudad(dto.ciudad());
        usuario.setDireccion(dto.direccion());
        usuario.setEmail(dto.email());
    }

    // Método auxiliar para mapear ObjectId -> String
    private String map(ObjectId value) {
        return value != null ? value.toHexString() : null;
    }

    // Método auxiliar para mapear String -> ObjectId
    private ObjectId map(String value) {
        return value != null ? new ObjectId(value) : null;
    }
}