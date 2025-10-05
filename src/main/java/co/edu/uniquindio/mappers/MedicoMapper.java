package co.edu.uniquindio.mappers;

import co.edu.uniquindio.dto.medico.CrearMedicoDTO;
import co.edu.uniquindio.dto.medico.EditarMedicoDTO;
import co.edu.uniquindio.dto.medico.MedicoDTO;
import co.edu.uniquindio.models.documents.Medico;
import co.edu.uniquindio.models.enums.EstadoUsuario;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class MedicoMapper {

    public Medico toDocument(CrearMedicoDTO dto) {
        Medico medico = new Medico();

        // Campos básicos
        medico.setIdentificacion(dto.identificacion());
        medico.setNombre(dto.nombre());
        medico.setEmail(dto.email());
        medico.setTelefono(dto.telefono());
        medico.setCiudad(dto.ciudad());
        medico.setDireccion(dto.direccion());
        medico.setPassword(dto.password());

        // Campos específicos de médico
        medico.setEspecialidad(dto.especialidad());
        medico.setRegistroMedico(dto.registroMedico());
        medico.setHoraInicio(dto.horaInicio());
        medico.setHoraFin(dto.horaFin());
        medico.setAñosExperiencia(dto.añosExperiencia());
        medico.setConsultorio(dto.consultorio());

        // Estados por defecto (se hereda estado de cuenta de Usuario)
        medico.setEstadoProfesional(true); // Estado profesional activo por defecto

        return medico;
    }

    public MedicoDTO toDTO(Medico medico) {
        return new MedicoDTO(
                map(medico.getCodigo()),
                medico.getNombre(),
                medico.getIdentificacion(),
                medico.getTelefono(),
                medico.getCiudad(),
                medico.getDireccion(),
                medico.getEmail(),

                // Campos específicos
                medico.getEspecialidad(),
                medico.getRegistroMedico(),
                medico.getHoraInicio(),
                medico.getHoraFin(),
                medico.getAñosExperiencia(),
                medico.getCalificacion(),
                medico.getEstado(), // ← Ahora es EstadoUsuario, no Boolean
                medico.getEstadoProfesional(), // ← Este sí es Boolean
                medico.getConsultorio()
        );
    }

    public void toDocument(EditarMedicoDTO dto, Medico medico) {
        // Campos básicos editables
        medico.setNombre(dto.nombre());
        medico.setTelefono(dto.telefono());
        medico.setCiudad(dto.ciudad());
        medico.setDireccion(dto.direccion());
        medico.setEmail(dto.email());

        // Campos profesionales editables
        if (dto.especialidad() != null) {
            medico.setEspecialidad(dto.especialidad());
        }
        if (dto.horaInicio() != null) {
            medico.setHoraInicio(dto.horaInicio());
        }
        if (dto.horaFin() != null) {
            medico.setHoraFin(dto.horaFin());
        }
        if (dto.añosExperiencia() != null) {
            medico.setAñosExperiencia(dto.añosExperiencia());
        }
        if (dto.consultorio() != null) {
            medico.setConsultorio(dto.consultorio());
        }
        if (dto.estado() != null) {
            medico.setEstado(dto.estado());
        }
    }

    // Método auxiliar para mapear ObjectId -> String
    private String map(ObjectId value) {
        return value != null ? value.toHexString() : null;
    }
}