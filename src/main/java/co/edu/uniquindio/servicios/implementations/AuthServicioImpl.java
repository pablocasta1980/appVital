package co.edu.uniquindio.servicios.implementations;

import co.edu.uniquindio.dto.auth.LoginDTO;
import co.edu.uniquindio.dto.auth.TokenDTO;
import co.edu.uniquindio.models.documents.Medico;
import co.edu.uniquindio.models.documents.Paciente;
import co.edu.uniquindio.repository.MedicoRepo;
import co.edu.uniquindio.repository.PacienteRepo;
import co.edu.uniquindio.seguridad.JWTUtils;
import co.edu.uniquindio.servicios.interfaces.AuthServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServicioImpl implements AuthServicio {

    private final PacienteRepo pacienteRepo;
    private final MedicoRepo medicoRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    @Override
    public TokenDTO login(LoginDTO loginDTO) throws Exception {
        // Primero buscar como paciente
        Optional<Paciente> optionalPaciente = pacienteRepo.findByEmail(loginDTO.email());
        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();
            if (passwordEncoder.matches(loginDTO.password(), paciente.getPassword())) {
                String token = jwtUtils.generateToken(paciente.getCodigo().toString(), crearClaimsPaciente(paciente));
                return new TokenDTO(token);
            }
        }

        // Si no es paciente, buscar como médico
        Optional<Medico> optionalMedico = medicoRepo.findByEmail(loginDTO.email());
        if (optionalMedico.isPresent()) {
            Medico medico = optionalMedico.get();
            if (passwordEncoder.matches(loginDTO.password(), medico.getPassword())) {
                // Verificar si el médico está activo
                if (!medico.getEstado()) {
                    throw new Exception("Médico inactivo. Contacte al administrador.");
                }
                String token = jwtUtils.generateToken(medico.getCodigo().toString(), crearClaimsMedico(medico));
                return new TokenDTO(token);
            }
        }

        // Si no se encuentra en ninguna colección
        throw new Exception("Credenciales incorrectas");
    }

    private Map<String, String> crearClaimsPaciente(Paciente paciente) {
        return Map.of(
                "email", paciente.getEmail(),
                "nombre", paciente.getNombre(),
                "rol", "ROLE_PACIENTE",
                "tipo", "PACIENTE"
        );
    }

    private Map<String, String> crearClaimsMedico(Medico medico) {
        return Map.of(
                "email", medico.getEmail(),
                "nombre", medico.getNombre(),
                "rol", "ROLE_MEDICO",
                "tipo", "MEDICO",
                "especialidad", medico.getEspecialidad().name()
        );
    }
}