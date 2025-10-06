package co.edu.uniquindio.servicios.implementations;

import co.edu.uniquindio.dto.EmailDTO;
import co.edu.uniquindio.dto.auth.ConfirmacionDTO;
import co.edu.uniquindio.dto.auth.LoginDTO;
import co.edu.uniquindio.dto.auth.RespuestaLoginDTO;
import co.edu.uniquindio.dto.auth.TokenDTO;
import co.edu.uniquindio.models.documents.Medico;
import co.edu.uniquindio.models.documents.Paciente;
import co.edu.uniquindio.models.documents.Usuario;
import co.edu.uniquindio.models.enums.EstadoUsuario;
import co.edu.uniquindio.repository.MedicoRepo;
import co.edu.uniquindio.repository.PacienteRepo;
import co.edu.uniquindio.seguridad.JWTUtils;
import co.edu.uniquindio.servicios.interfaces.AuthServicio;
import co.edu.uniquindio.servicios.interfaces.EmailServicio;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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
    private final EmailServicio emailServicio;

    @Override
    public TokenDTO login(LoginDTO loginDTO) throws Exception {
        // Primero buscar como paciente
        Optional<Paciente> optionalPaciente = pacienteRepo.findByEmail(loginDTO.email());
        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();

            if (paciente.getEstado() != EstadoUsuario.ACTIVO) {
                throw new Exception("Cuenta no confirmada. Revise su email para activar la cuenta.");
            }

            if (passwordEncoder.matches(loginDTO.password(), paciente.getPassword())) {
                String token = jwtUtils.generateToken(paciente.getCodigo().toString(), crearClaimsPaciente(paciente));

                // ✅ DEVOLVER TOKENDTO SIN TIPOUSUARIO
                return new TokenDTO(
                        token,
                        paciente.getCodigo().toString(),  // id
                        paciente.getNombre(),             // nombre
                        paciente.getEmail(),              // email
                        paciente.getRol().name()          // rol
                        // ❌ Quitamos tipoUsuario
                );
            }
        }

        // Si no es paciente, buscar como médico
        Optional<Medico> optionalMedico = medicoRepo.findByEmail(loginDTO.email());
        if (optionalMedico.isPresent()) {
            Medico medico = optionalMedico.get();

            if (medico.getEstado() != EstadoUsuario.ACTIVO) {
                throw new Exception("Cuenta no confirmada. Revise su email para activar la cuenta.");
            }

            if (!medico.getEstadoProfesional()) {
                throw new Exception("Médico inactivo. Contacte al administrador.");
            }

            if (passwordEncoder.matches(loginDTO.password(), medico.getPassword())) {
                String token = jwtUtils.generateToken(medico.getCodigo().toString(), crearClaimsMedico(medico));

                // ✅ DEVOLVER TOKENDTO SIN TIPOUSUARIO
                return new TokenDTO(
                        token,
                        medico.getCodigo().toString(),    // id
                        medico.getNombre(),               // nombre
                        medico.getEmail(),                // email
                        medico.getRol().name()            // rol

                );
            }
        }

        throw new Exception("Credenciales incorrectas");
    }

    @Override
    public void confirmarCuenta(ConfirmacionDTO confirmacionDTO) throws Exception {
        try {
            // Validar el token de confirmación
            Jws<Claims> payload = jwtUtils.parseJwt(confirmacionDTO.token());
            String userId = payload.getPayload().getSubject();
            String tipoUsuario = payload.getPayload().get("tipo", String.class);

            if ("PACIENTE".equals(tipoUsuario)) {
                ObjectId objectId = new ObjectId(userId);
                Paciente paciente = pacienteRepo.findById(objectId)
                        .orElseThrow(() -> new Exception("Paciente no encontrado"));
                paciente.setEstado(EstadoUsuario.ACTIVO);
                pacienteRepo.save(paciente);

            } else if ("MEDICO".equals(tipoUsuario)) {
                ObjectId objectId = new ObjectId(userId);
                Medico medico = medicoRepo.findById(objectId)
                        .orElseThrow(() -> new Exception("Médico no encontrado"));
                medico.setEstado(EstadoUsuario.ACTIVO);
                medicoRepo.save(medico);
            } else {
                throw new Exception("Tipo de usuario no válido");
            }

        } catch (ExpiredJwtException e) {
            throw new Exception("El token de confirmación ha expirado. Solicite uno nuevo.");
        } catch (Exception e) {
            throw new Exception("Token de confirmación inválido: " + e.getMessage());
        }
    }

    @Override
    public void reenviarCodigoConfirmacion(String email) throws Exception {
        // Buscar en pacientes
        Optional<Paciente> optionalPaciente = pacienteRepo.findByEmail(email);
        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();
            if (paciente.getEstado() == EstadoUsuario.ACTIVO) {
                throw new Exception("La cuenta ya está activa");
            }
            enviarEmailConfirmacion(paciente);
            return;
        }

        // Buscar en médicos
        Optional<Medico> optionalMedico = medicoRepo.findByEmail(email);
        if (optionalMedico.isPresent()) {
            Medico medico = optionalMedico.get();
            if (medico.getEstado() == EstadoUsuario.ACTIVO) {
                throw new Exception("La cuenta ya está activa");
            }
            enviarEmailConfirmacion(medico);
            return;
        }

        throw new Exception("Email no registrado");
    }

    // MÉTODO PARA ENVIAR EMAIL DE CONFIRMACIÓN
    private void enviarEmailConfirmacion(Usuario usuario) throws Exception {
        // Generar token de confirmación (válido por 24 horas)
        Map<String, String> claims = Map.of(
                "tipo", usuario.getRol().name(),
                "accion", "CONFIRMACION_CUENTA"
        );
        String tokenConfirmacion = jwtUtils.generateToken(usuario.getCodigo().toString(), claims);

        // Crear enlace de confirmación (para desarrollo)
        String enlaceConfirmacion = "http://localhost:8081/api/auth/confirmar";

        // ENVIAR EMAIL REAL
        EmailDTO email = new EmailDTO(
                "Confirma tu cuenta - Sistema de Salud",
                "Hola " + usuario.getNombre() + ",\n\n" +
                        "Para activar tu cuenta, usa el siguiente token:\n\n" +
                        "TOKEN: " + tokenConfirmacion + "\n\n" +
                        "O haz clic en este enlace para activar cuenta:\n\n" +
                        enlaceConfirmacion + "?token=" + tokenConfirmacion + "\n\n" +
                        "Este token expirará en 24 horas.\n\n" +
                        "Si no solicitaste este registro, ignora este mensaje.\n\n" +
                        "Saludos cordiales,\nEquipo de Sistema de Salud",
                usuario.getEmail()
        );

        emailServicio.enviarCorreo(email);
        System.out.println("✅ Email de confirmación enviado a: " + usuario.getEmail());
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