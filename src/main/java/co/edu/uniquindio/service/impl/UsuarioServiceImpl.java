package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.domain.dto.request.RegistroPacienteRequest;
import co.edu.uniquindio.domain.dto.response.UsuarioResponse;
import co.edu.uniquindio.domain.model.Usuario;
import co.edu.uniquindio.domain.repository.UsuarioRepository;
import co.edu.uniquindio.service.interfaces.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponse registrarPaciente(RegistroPacienteRequest request) {
        // Verificar si el email ya existe
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear nuevo usuario como PACIENTE por defecto
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .telefono(request.getTelefono())
                .ciudad(request.getCiudad())
                .direccion(request.getDireccion())
                .tipoUsuario("PACIENTE") // Siempre será paciente
                .fechaRegistro(LocalDateTime.now())
                .activo(true)
                .build();

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirAResponse(usuarioGuardado);
    }

    @Override
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    private UsuarioResponse convertirAResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .ciudad(usuario.getCiudad())
                .direccion(usuario.getDireccion())
                .tipoUsuario(usuario.getTipoUsuario())
                .build();
    }
}