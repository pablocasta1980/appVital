package co.edu.uniquindio.servicios.implementations;

import co.edu.uniquindio.dto.paciente.CrearPacienteDTO;
import co.edu.uniquindio.dto.paciente.EditarPacienteDTO;
import co.edu.uniquindio.dto.paciente.PacienteDTO;
import co.edu.uniquindio.mappers.PacienteMapper;
import co.edu.uniquindio.models.documents.Paciente;
import co.edu.uniquindio.models.enums.EstadoUsuario;
import co.edu.uniquindio.repository.PacienteRepo;
import co.edu.uniquindio.servicios.interfaces.AuthServicio; // ← NUEVO IMPORT
import co.edu.uniquindio.servicios.interfaces.PacienteServicio;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteServicioImpl implements PacienteServicio {

    private final PacienteRepo pacienteRepo;
    private final PacienteMapper pacienteMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthServicio authServicio; // ← NUEVO: Inyectar AuthServicio

    @Override
    public void crear(CrearPacienteDTO pacienteDTO) throws Exception {
        if (existeEmail(pacienteDTO.email())) {
            throw new Exception("El email ya está registrado");
        }
        if (existeIdentificacion(pacienteDTO.identificacion())) {
            throw new Exception("La identificación ya está registrada");
        }

        Paciente paciente = pacienteMapper.toDocument(pacienteDTO);
        paciente.setPassword(passwordEncoder.encode(pacienteDTO.password()));
        paciente.setEstado(EstadoUsuario.INACTIVO); // Estado inicial
        pacienteRepo.save(paciente);

        // Enviar email de confirmación usando AuthServicio
        authServicio.reenviarCodigoConfirmacion(paciente.getEmail());
    }

    @Override
    public void editar(String id, EditarPacienteDTO pacienteDTO) throws Exception {
        ObjectId objectId = new ObjectId(id);
        Paciente paciente = pacienteRepo.findById(objectId)
                .orElseThrow(() -> new Exception("Paciente no encontrado"));

        // Validar email si cambió
        if (!paciente.getEmail().equals(pacienteDTO.email()) && existeEmail(pacienteDTO.email())) {
            throw new Exception("El email ya está registrado por otro usuario");
        }

        pacienteMapper.toDocument(pacienteDTO, paciente);
        pacienteRepo.save(paciente);
    }

    @Override
    public void eliminar(String id) throws Exception {
        ObjectId objectId = new ObjectId(id);
        if (!pacienteRepo.existsById(objectId)) {
            throw new Exception("Paciente no encontrado");
        }
        pacienteRepo.deleteById(objectId);
    }

    @Override
    public void eliminarPorIdentificacion(String identificacion) throws Exception {
        Paciente paciente = pacienteRepo.findByIdentificacion(identificacion)
                .orElseThrow(() -> new Exception("Paciente no encontrado"));
        pacienteRepo.delete(paciente);
    }

    @Override
    public PacienteDTO obtener(String id) throws Exception {
        ObjectId objectId = new ObjectId(id);
        Paciente paciente = pacienteRepo.findById(objectId)
                .orElseThrow(() -> new Exception("Paciente no encontrado"));
        return pacienteMapper.toDTO(paciente);
    }

    @Override
    public PacienteDTO obtenerPorIdentificacion(String identificacion) throws Exception {
        Paciente paciente = pacienteRepo.findByIdentificacion(identificacion)
                .orElseThrow(() -> new Exception("Paciente no encontrado"));
        return pacienteMapper.toDTO(paciente);
    }

    private boolean existeEmail(String email) {
        return pacienteRepo.existsByEmail(email);
    }

    private boolean existeIdentificacion(String identificacion) {
        return pacienteRepo.existsByIdentificacion(identificacion);
    }
}