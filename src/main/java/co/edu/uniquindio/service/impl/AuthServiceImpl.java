// src/main/java/co/edu/uniquindio/service/impl/AuthServiceImpl.java
package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.config.JwtUtils;
import co.edu.uniquindio.domain.dto.request.LoginRequest;
import co.edu.uniquindio.domain.dto.response.AuthResponse;
import co.edu.uniquindio.domain.dto.response.UsuarioResponse;
import co.edu.uniquindio.domain.model.Usuario;
import co.edu.uniquindio.domain.repository.UsuarioRepository;
import co.edu.uniquindio.service.interfaces.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        // Buscar usuario por email
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmailAndActivoTrue(loginRequest.getEmail());

        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Usuario usuario = usuarioOptional.get();

        // Verificar contraseña
        if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Generar token JWT real
        String jwtToken = jwtUtils.generateToken(usuario.getEmail());

        // Crear respuesta con JWT real
        UsuarioResponse usuarioResponse = UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .ciudad(usuario.getCiudad())
                .direccion(usuario.getDireccion())
                .tipoUsuario(usuario.getTipoUsuario())
                .especialidad(usuario.getEspecialidad())
                .build();

        return AuthResponse.builder()
                .token(jwtToken) // ✅ JWT real
                .tipoToken("Bearer")
                .usuario(usuarioResponse)
                .build();
    }
}