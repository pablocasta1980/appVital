package co.edu.uniquindio.servicios.implementations;

import co.edu.uniquindio.dto.medico.CrearMedicoDTO;
import co.edu.uniquindio.dto.medico.EditarMedicoDTO;
import co.edu.uniquindio.dto.medico.MedicoDTO;
import co.edu.uniquindio.mappers.MedicoMapper;
import co.edu.uniquindio.models.documents.Medico;
import co.edu.uniquindio.models.enums.Especialidad;
import co.edu.uniquindio.repository.MedicoRepo;
import co.edu.uniquindio.servicios.interfaces.MedicoServicio;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicoServicioImpl implements MedicoServicio {

    private final MedicoRepo medicoRepo;
    private final MedicoMapper medicoMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void crear(CrearMedicoDTO medicoDTO) throws Exception {
        if (existeEmail(medicoDTO.email())) {
            throw new Exception("El email ya está registrado");
        }
        if (existeIdentificacion(medicoDTO.identificacion())) {
            throw new Exception("La identificación ya está registrada");
        }

        Medico medico = medicoMapper.toDocument(medicoDTO);
        medico.setPassword(passwordEncoder.encode(medicoDTO.password()));
        medicoRepo.save(medico);
    }

    @Override
    public void editar(String id, EditarMedicoDTO medicoDTO) throws Exception {
        ObjectId objectId = new ObjectId(id);
        Medico medico = medicoRepo.findById(objectId)
                .orElseThrow(() -> new Exception("Médico no encontrado"));

        // Validar email si cambió
        if (!medico.getEmail().equals(medicoDTO.email()) && existeEmail(medicoDTO.email())) {
            throw new Exception("El email ya está registrado por otro usuario");
        }

        medicoMapper.toDocument(medicoDTO, medico);
        medicoRepo.save(medico);
    }

    @Override
    public void eliminar(String id) throws Exception {
        ObjectId objectId = new ObjectId(id);
        if (!medicoRepo.existsById(objectId)) {
            throw new Exception("Médico no encontrado");
        }
        medicoRepo.deleteById(objectId);
    }

    @Override
    public void eliminarPorIdentificacion(String identificacion) throws Exception {
        Medico medico = medicoRepo.findByIdentificacion(identificacion)
                .orElseThrow(() -> new Exception("Médico no encontrado"));
        medicoRepo.delete(medico);
    }

    @Override
    public MedicoDTO obtener(String id) throws Exception {
        ObjectId objectId = new ObjectId(id);
        Medico medico = medicoRepo.findById(objectId)
                .orElseThrow(() -> new Exception("Médico no encontrado"));
        return medicoMapper.toDTO(medico);
    }

    @Override
    public MedicoDTO obtenerPorIdentificacion(String identificacion) throws Exception {
        Medico medico = medicoRepo.findByIdentificacion(identificacion)
                .orElseThrow(() -> new Exception("Médico no encontrado"));
        return medicoMapper.toDTO(medico);
    }

    @Override
    public List<MedicoDTO> listarTodos() throws Exception {
        return medicoRepo.findAll().stream()
                .map(medicoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicoDTO> listarPorEspecialidad(Especialidad especialidad) throws Exception {
        return medicoRepo.findByEspecialidad(especialidad).stream()
                .map(medicoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicoDTO> listarActivos() throws Exception {
        return medicoRepo.findByEstado(true).stream()
                .map(medicoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void cambiarEstado(String id, Boolean estado) throws Exception {
        ObjectId objectId = new ObjectId(id);
        Medico medico = medicoRepo.findById(objectId)
                .orElseThrow(() -> new Exception("Médico no encontrado"));

        // Cambiar el estado PROFESIONAL (no el estado de cuenta)
        medico.setEstadoProfesional(estado); // ← Cambiar estadoProfesional, no estado
        medicoRepo.save(medico);
    }

    private boolean existeEmail(String email) {
        return medicoRepo.existsByEmail(email);
    }

    private boolean existeIdentificacion(String identificacion) {
        return medicoRepo.existsByIdentificacion(identificacion);
    }
}