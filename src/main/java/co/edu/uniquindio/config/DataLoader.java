// src/main/java/co/edu/uniquindio/config/DataLoader.java
package co.edu.uniquindio.config;

import co.edu.uniquindio.domain.model.Usuario;
import co.edu.uniquindio.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen usuarios para no duplicar
        if (usuarioRepository.count() == 0) {
            crearUsuariosDePrueba();
        }
    }

    private void crearUsuariosDePrueba() {
        // Usuario Paciente
        Usuario paciente = Usuario.builder()
                .nombre("María González")
                .email("paciente@uniquindio.edu.co")
                .password(passwordEncoder.encode("password123"))
                .telefono("3124567890")
                .ciudad("Armenia")
                .direccion("Calle 123 #45-67")
                .tipoUsuario("paciente")
                .fechaRegistro(LocalDateTime.now())
                .activo(true)
                .build();

        // Usuario Médico
        Usuario medico = Usuario.builder()
                .nombre("Dr. Carlos Rodríguez")
                .email("medico@uniquindio.edu.co")
                .password(passwordEncoder.encode("password123"))
                .telefono("3156789012")
                .ciudad("Armenia")
                .direccion("Av. Bolívar #23-45")
                .tipoUsuario("medico")
                .especialidad("Cardiología")
                .numeroLicencia("MED-123456")
                .fechaRegistro(LocalDateTime.now())
                .activo(true)
                .build();

        // Usuario Administrativo
        Usuario administrativo = Usuario.builder()
                .nombre("Ana Martínez")
                .email("admin@uniquindio.edu.co")
                .password(passwordEncoder.encode("password123"))
                .telefono("3145678901")
                .ciudad("Armenia")
                .direccion("Carrera 14 #32-10")
                .tipoUsuario("administrativo")
                .fechaRegistro(LocalDateTime.now())
                .activo(true)
                .build();

        usuarioRepository.save(paciente);
        usuarioRepository.save(medico);
        usuarioRepository.save(administrativo);

        System.out.println("✅ Usuarios de prueba creados exitosamente");
        System.out.println("📧 Paciente: paciente@uniquindio.edu.co / password123");
        System.out.println("👨‍⚕️ Médico: medico@uniquindio.edu.co / password123");
        System.out.println("👩‍💼 Admin: admin@uniquindio.edu.co / password123");
    }
}