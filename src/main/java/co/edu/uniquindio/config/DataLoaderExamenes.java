package co.edu.uniquindio.config;

import co.edu.uniquindio.domain.model.Examen;
import co.edu.uniquindio.domain.repository.ExamenRepository;
import co.edu.uniquindio.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataLoaderExamenes implements CommandLineRunner {

    private final ExamenRepository examenRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (examenRepository.count() == 0) {
            crearExamenesDePrueba();
        }
    }

    private void crearExamenesDePrueba() {
        var paciente = usuarioRepository.findByEmail("paciente@uniquindio.edu.co")
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // Examen 1 - Hemograma Completo
        Map<String, String> resultados1 = new HashMap<>();
        resultados1.put("hemoglobina", "14.2 g/dL");
        resultados1.put("hematocrito", "42%");
        resultados1.put("leucocitos", "6.800 /mm³");
        resultados1.put("plaquetas", "250.000 /mm³");

        Examen examen1 = new Examen();
        examen1.setPacienteId(paciente.getId());
        examen1.setTipo("Hemograma Completo");
        examen1.setFechaSolicitud(LocalDateTime.now().minusDays(10));
        examen1.setFechaRealizacion(LocalDateTime.now().minusDays(9));
        examen1.setFechaResultado(LocalDateTime.now().minusDays(8));
        examen1.setLaboratorio("Laboratorio Central Armenia");
        examen1.setMedicoSolicitante("Dr. Carlos Rodríguez");
        examen1.setEstado("COMPLETADO");
        examen1.setResultados(resultados1);
        examen1.setArchivo("hemograma_2024_001.pdf");
        examen1.setUrgencia(false);
        examen1.setObservaciones("Dentro de rangos normales");

        // Examen 2 - Perfil Lipídico
        Map<String, String> resultados2 = new HashMap<>();
        resultados2.put("colesterolTotal", "185 mg/dL");
        resultados2.put("colesterolHDL", "55 mg/dL");
        resultados2.put("colesterolLDL", "110 mg/dL");
        resultados2.put("trigliceridos", "120 mg/dL");

        Examen examen2 = new Examen();
        examen2.setPacienteId(paciente.getId());
        examen2.setTipo("Perfil Lipídico");
        examen2.setFechaSolicitud(LocalDateTime.now().minusDays(5));
        examen2.setFechaRealizacion(LocalDateTime.now().minusDays(4));
        examen2.setFechaResultado(LocalDateTime.now().minusDays(3));
        examen2.setLaboratorio("Laboratorio Clínico Uniquindío");
        examen2.setMedicoSolicitante("Dra. Ana Martínez");
        examen2.setEstado("COMPLETADO");
        examen2.setResultados(resultados2);
        examen2.setArchivo("perfil_lipidico_2024_002.pdf");
        examen2.setUrgencia(false);
        examen2.setObservaciones("Perfil lipídico óptimo");

        // Examen 3 - Tomografía (en proceso)
        Examen examen3 = new Examen();
        examen3.setPacienteId(paciente.getId());
        examen3.setTipo("Tomografía Computarizada");
        examen3.setFechaSolicitud(LocalDateTime.now().minusDays(2));
        examen3.setFechaRealizacion(LocalDateTime.now().minusDays(1));
        examen3.setFechaResultado(null);
        examen3.setLaboratorio("Centro de Imágenes Médicas");
        examen3.setMedicoSolicitante("Dr. Javier López");
        examen3.setEstado("EN_PROCESO");
        examen3.setResultados(null);
        examen3.setArchivo(null);
        examen3.setUrgencia(true);
        examen3.setObservaciones("En proceso de análisis");

        examenRepository.save(examen1);
        examenRepository.save(examen2);
        examenRepository.save(examen3);

        System.out.println("✅ Exámenes de prueba creados exitosamente");
    }
}