package co.edu.uniquindio.config;

import co.edu.uniquindio.domain.model.Notificacion;
import co.edu.uniquindio.domain.repository.NotificacionRepository;
import co.edu.uniquindio.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataLoaderNotificaciones implements CommandLineRunner {

    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (notificacionRepository.count() == 0) {
            crearNotificacionesDePrueba();
        }
    }

    private void crearNotificacionesDePrueba() {
        var paciente = usuarioRepository.findByEmail("paciente@uniquindio.edu.co")
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // Notificación 1 - Recordatorio de cita
        Notificacion notificacion1 = new Notificacion();
        notificacion1.setPacienteId(paciente.getId());
        notificacion1.setTitulo("Recordatorio de Cita Médica");
        notificacion1.setMensaje("Tiene una cita programada para mañana a las 10:00 AM con el Dr. Carlos Rodríguez");
        notificacion1.setTipo("RECORDATORIO");
        notificacion1.setFechaCreacion(LocalDateTime.now().minusHours(2));
        notificacion1.setLeida(false);
        notificacion1.setUrgente(false);
        notificacion1.setRelacionTipo("CITA");

        // Notificación 2 - Resultado de examen listo
        Notificacion notificacion2 = new Notificacion();
        notificacion2.setPacienteId(paciente.getId());
        notificacion2.setTitulo("Resultado de Examen Disponible");
        notificacion2.setMensaje("Su examen de Hemograma Completo está listo. Puede descargar los resultados.");
        notificacion2.setTipo("RESULTADO");
        notificacion2.setFechaCreacion(LocalDateTime.now().minusDays(1));
        notificacion2.setLeida(false);
        notificacion2.setUrgente(false);
        notificacion2.setRelacionTipo("EXAMEN");

        // Notificación 3 - Alerta de medicamento
        Notificacion notificacion3 = new Notificacion();
        notificacion3.setPacienteId(paciente.getId());
        notificacion3.setTitulo("Recordatorio de Medicamento");
        notificacion3.setMensaje("Recuerde tomar su medicamento recetado a las 8:00 PM");
        notificacion3.setTipo("ALERTA");
        notificacion3.setFechaCreacion(LocalDateTime.now().minusHours(6));
        notificacion3.setLeida(true);
        notificacion3.setUrgente(false);
        notificacion3.setRelacionTipo("MEDICAMENTO");

        // Notificación 4 - Información general
        Notificacion notificacion4 = new Notificacion();
        notificacion4.setPacienteId(paciente.getId());
        notificacion4.setTitulo("Bienvenido a AppVital");
        notificacion4.setMensaje("Su cuenta ha sido activada exitosamente. Ahora puede acceder a todos nuestros servicios.");
        notificacion4.setTipo("INFORMACION");
        notificacion4.setFechaCreacion(LocalDateTime.now().minusDays(3));
        notificacion4.setLeida(true);
        notificacion4.setUrgente(false);

        // Notificación 5 - Urgente
        Notificacion notificacion5 = new Notificacion();
        notificacion5.setPacienteId(paciente.getId());
        notificacion5.setTitulo("⚠️ Alerta Importante");
        notificacion5.setMensaje("Se ha detectado una interacción medicamentosa. Por favor contacte a su médico.");
        notificacion5.setTipo("ALERTA");
        notificacion5.setFechaCreacion(LocalDateTime.now().minusHours(1));
        notificacion5.setLeida(false);
        notificacion5.setUrgente(true);

        notificacionRepository.save(notificacion1);
        notificacionRepository.save(notificacion2);
        notificacionRepository.save(notificacion3);
        notificacionRepository.save(notificacion4);
        notificacionRepository.save(notificacion5);

        System.out.println("✅ Notificaciones de prueba creadas exitosamente");
    }
}