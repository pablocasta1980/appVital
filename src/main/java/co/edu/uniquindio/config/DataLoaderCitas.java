package co.edu.uniquindio.config;

import co.edu.uniquindio.domain.model.Cita;
import co.edu.uniquindio.domain.repository.CitaRepository;
import co.edu.uniquindio.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoaderCitas implements CommandLineRunner {

    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si ya existen citas para no duplicar
        if (citaRepository.count() == 0) {
            crearCitasDePrueba();
        }
    }

    private void crearCitasDePrueba() {
        // Obtener los usuarios de prueba
        var paciente = usuarioRepository.findByEmail("paciente@uniquindio.edu.co")
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        var medico = usuarioRepository.findByEmail("medico@uniquindio.edu.co")
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));

        // Cita 1 - Pendiente
        Cita cita1 = new Cita();
        cita1.setPacienteId(paciente.getId());
        cita1.setMedicoId(medico.getId());
        cita1.setMedicoNombre("Dr. Carlos Rodríguez");
        cita1.setEspecialidad("Cardiología");
        cita1.setFecha(LocalDateTime.now().plusDays(2).withHour(10).withMinute(0));
        cita1.setEstado("PENDIENTE");
        cita1.setTipo("PRESENCIAL");
        cita1.setMotivo("Control de presión arterial");
        cita1.setConsultorio("Consultorio 201");
        cita1.setFechaCreacion(LocalDateTime.now());
        cita1.setFechaActualizacion(LocalDateTime.now());

        // Cita 2 - Confirmada
        Cita cita2 = new Cita();
        cita2.setPacienteId(paciente.getId());
        cita2.setMedicoId(medico.getId());
        cita2.setMedicoNombre("Dr. Carlos Rodríguez");
        cita2.setEspecialidad("Cardiología");
        cita2.setFecha(LocalDateTime.now().plusDays(5).withHour(14).withMinute(30));
        cita2.setEstado("CONFIRMADA");
        cita2.setTipo("VIRTUAL");
        cita2.setMotivo("Seguimiento de tratamiento");
        cita2.setConsultorio("Plataforma Online");
        cita2.setFechaCreacion(LocalDateTime.now());
        cita2.setFechaActualizacion(LocalDateTime.now());

        // Cita 3 - Completada
        Cita cita3 = new Cita();
        cita3.setPacienteId(paciente.getId());
        cita3.setMedicoId(medico.getId());
        cita3.setMedicoNombre("Dr. Carlos Rodríguez");
        cita3.setEspecialidad("Cardiología");
        cita3.setFecha(LocalDateTime.now().minusDays(7).withHour(9).withMinute(0));
        cita3.setEstado("COMPLETADA");
        cita3.setTipo("PRESENCIAL");
        cita3.setMotivo("Consulta general");
        cita3.setConsultorio("Consultorio 105");
        cita3.setFechaCreacion(LocalDateTime.now().minusDays(10));
        cita3.setFechaActualizacion(LocalDateTime.now().minusDays(7));

        citaRepository.save(cita1);
        citaRepository.save(cita2);
        citaRepository.save(cita3);

        System.out.println("✅ Citas de prueba creadas exitosamente");
        System.out.println("📅 Se crearon 3 citas para el paciente: " + paciente.getNombre());
    }
}