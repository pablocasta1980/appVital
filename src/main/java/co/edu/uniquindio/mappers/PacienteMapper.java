package co.edu.uniquindio.mappers;

import co.edu.uniquindio.dto.paciente.CrearPacienteDTO;
import co.edu.uniquindio.dto.paciente.EditarPacienteDTO;
import co.edu.uniquindio.dto.paciente.PacienteDTO;
import co.edu.uniquindio.models.documents.Paciente;
import co.edu.uniquindio.models.enums.EstadoUsuario;
import co.edu.uniquindio.models.enums.RolUsuario;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public Paciente toDocument(CrearPacienteDTO dto) {
        Paciente paciente = new Paciente();

        // Campos básicos
        paciente.setIdentificacion(dto.identificacion());
        paciente.setNombre(dto.nombre());
        paciente.setEmail(dto.email());
        paciente.setTelefono(dto.telefono());
        paciente.setCiudad(dto.ciudad());
        paciente.setDireccion(dto.direccion());
        paciente.setPassword(dto.password()); // Se codificará después

        // Campos específicos de paciente
        paciente.setFechaNacimiento(dto.fechaNacimiento());
        paciente.setAlergias(dto.alergias());
        paciente.setEps(dto.eps());
        paciente.setTipoSangre(dto.tipoSangre());
        paciente.setPeso(dto.peso());
        paciente.setAltura(dto.altura());
        paciente.setContactoEmergencia(dto.contactoEmergencia());
        paciente.setTelefonoEmergencia(dto.telefonoEmergencia());
        paciente.setRol(RolUsuario.PACIENTE);
        paciente.setEstado(EstadoUsuario.INACTIVO); // ← NUEVO

        return paciente;
    }

    public PacienteDTO toDTO(Paciente paciente) {
        return new PacienteDTO(
                map(paciente.getCodigo()),
                paciente.getNombre(),
                paciente.getIdentificacion(),
                paciente.getTelefono(),
                paciente.getCiudad(),
                paciente.getDireccion(),
                paciente.getEmail(),

                // Campos específicos
                paciente.getFechaNacimiento(),
                paciente.getAlergias(),
                paciente.getEps(),
                paciente.getTipoSangre(),
                paciente.getPeso(),
                paciente.getAltura(),
                paciente.getContactoEmergencia(),
                paciente.getTelefonoEmergencia()
        );
    }

    public void toDocument(EditarPacienteDTO dto, Paciente paciente) {
        // Campos básicos editables
        paciente.setNombre(dto.nombre());
        paciente.setTelefono(dto.telefono());
        paciente.setCiudad(dto.ciudad());
        paciente.setDireccion(dto.direccion());
        paciente.setEmail(dto.email());

        // Campos médicos editables
        if (dto.fechaNacimiento() != null) {
            paciente.setFechaNacimiento(dto.fechaNacimiento());
        }
        if (dto.alergias() != null) {
            paciente.setAlergias(dto.alergias());
        }
        if (dto.eps() != null) {
            paciente.setEps(dto.eps());
        }
        if (dto.tipoSangre() != null) {
            paciente.setTipoSangre(dto.tipoSangre());
        }
        if (dto.peso() != null) {
            paciente.setPeso(dto.peso());
        }
        if (dto.altura() != null) {
            paciente.setAltura(dto.altura());
        }
        if (dto.contactoEmergencia() != null) {
            paciente.setContactoEmergencia(dto.contactoEmergencia());
        }
        if (dto.telefonoEmergencia() != null) {
            paciente.setTelefonoEmergencia(dto.telefonoEmergencia());
        }
    }

    // Método auxiliar para mapear ObjectId -> String
    private String map(ObjectId value) {
        return value != null ? value.toHexString() : null;
    }
}