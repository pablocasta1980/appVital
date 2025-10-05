package co.edu.uniquindio.config;

import co.edu.uniquindio.models.documents.Medico;
import co.edu.uniquindio.models.enums.Especialidad;
import co.edu.uniquindio.models.enums.EstadoUsuario;
import co.edu.uniquindio.repository.MedicoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final MedicoRepo medicoRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (medicoRepo.findByEmail("cardiologo@hospital.com").isEmpty()) {
            crearMedicosPorDefecto();
        }
    }

    private void crearMedicosPorDefecto() {
        List<Medico> medicos = Arrays.asList(
                crearMedico(
                        "Dr. Carlos Rodríguez",
                        "1001001001",
                        "3001112233",
                        "cardiologo@hospital.com",
                        Especialidad.CARDIOLOGIA,
                        "RM-12345",
                        LocalTime.of(8, 0),
                        LocalTime.of(17, 0),
                        10,
                        "Consultorio 101"
                ),
                crearMedico(
                        "Dra. Ana Martínez",
                        "1001001002",
                        "3002223344",
                        "pediatra@hospital.com",
                        Especialidad.PEDIATRIA,
                        "RM-12346",
                        LocalTime.of(7, 0),
                        LocalTime.of(15, 0),
                        8,
                        "Consultorio 102"
                ),
                crearMedico(
                        "Dr. Luis García",
                        "1001001003",
                        "3003334455",
                        "dermatologo@hospital.com",
                        Especialidad.DERMATOLOGIA,
                        "RM-12347",
                        LocalTime.of(9, 0),
                        LocalTime.of(18, 0),
                        12,
                        "Consultorio 103"
                )
        );

        medicoRepo.saveAll(medicos);
        System.out.println("✅ 3 médicos creados por defecto");
    }

    private Medico crearMedico(String nombre, String identificacion, String telefono,
                               String email, Especialidad especialidad, String registroMedico,
                               LocalTime horaInicio, LocalTime horaFin, Integer experiencia, String consultorio) {
        Medico medico = new Medico();
        medico.setNombre(nombre);
        medico.setIdentificacion(identificacion);
        medico.setTelefono(telefono);
        medico.setEmail(email);
        medico.setPassword(passwordEncoder.encode("Medico123"));
        medico.setCiudad("Armenia");
        medico.setDireccion("Clínica Central");
        medico.setEspecialidad(especialidad);
        medico.setRegistroMedico(registroMedico);
        medico.setHoraInicio(horaInicio);
        medico.setHoraFin(horaFin);
        medico.setAñosExperiencia(experiencia);
        medico.setConsultorio(consultorio);
        medico.setCalificacion(5.0);
        medico.setEstadoProfesional(true); // ← Estado profesional (boolean)
        medico.setEstado(EstadoUsuario.ACTIVO); // ← Estado de cuenta (EstadoUsuario)
        return medico;
    }
}