package co.edu.uniquindio.config;

import co.edu.uniquindio.models.documents.Usuario;
import co.edu.uniquindio.models.enums.RolUsuario;
import co.edu.uniquindio.repository.UsuarioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepo usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen médicos para no duplicar
        if (usuarioRepo.findByEmail("cardiologo@hospital.com").isEmpty()) {
            crearMedicosPorDefecto();
        }
    }

    private void crearMedicosPorDefecto() {
        List<Usuario> medicos = Arrays.asList(
                crearMedico(
                        "Dr. Carlos Rodríguez",
                        "1001001001",
                        "3001112233",
                        "cardiologo@hospital.com",
                        "Cardiología"
                ),
                crearMedico(
                        "Dra. Ana Martínez",
                        "1001001002",
                        "3002223344",
                        "pediatra@hospital.com",
                        "Pediatría"
                ),
                crearMedico(
                        "Dr. Luis García",
                        "1001001003",
                        "3003334455",
                        "dermatologo@hospital.com",
                        "Dermatología"
                )
        );

        usuarioRepo.saveAll(medicos);
        System.out.println("✅ 3 médicos creados por defecto");
    }

    private Usuario crearMedico(String nombre, String identificacion, String telefono, String email, String especialidad) {
        Usuario medico = new Usuario();
        medico.setNombre(nombre);
        medico.setIdentificacion(identificacion);
        medico.setTelefono(telefono);
        medico.setEmail(email);
        medico.setPassword(passwordEncoder.encode("Medico123")); // Contraseña por defecto
        medico.setRol(RolUsuario.MEDICO);
        medico.setCiudad("Armenia");
        medico.setDireccion("Clínica Central - " + especialidad);
        return medico;
    }
}