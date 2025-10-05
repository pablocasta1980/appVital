package co.edu.uniquindio.mappers;

import co.edu.uniquindio.dto.CrearUsuarioDTO;
import co.edu.uniquindio.dto.EditarUsuarioDTO;
import co.edu.uniquindio.dto.UsuarioDTO;
import co.edu.uniquindio.models.documents.Usuario;
import co.edu.uniquindio.models.enums.RolUsuario;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toDocument(CrearUsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setIdentificacion(dto.identificacion());
        usuario.setTelefono(dto.telefono());
        usuario.setCiudad(dto.ciudad());
        usuario.setDireccion(dto.direccion());
        usuario.setEmail(dto.email());
        usuario.setPassword(dto.password());

        // Manejar rol: si es null o vacío, usar PACIENTE por defecto
        if (dto.rol() != null && !dto.rol().trim().isEmpty()) {
            try {
                usuario.setRol(RolUsuario.valueOf(dto.rol().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Si el rol no es válido, usar PACIENTE por defecto
                usuario.setRol(RolUsuario.PACIENTE);
            }
        } else {
            usuario.setRol(RolUsuario.PACIENTE); // ← Valor por defecto
        }

        return usuario;
    }

    public UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getNombre(),
                usuario.getIdentificacion(), // ← NUEVO
                usuario.getTelefono(),
                usuario.getCiudad(),
                usuario.getDireccion(),
                usuario.getEmail(),
                usuario.getRol().name() // ← Incluir el rol en el DTO
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