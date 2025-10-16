package co.edu.uniquindio.controller;

import co.edu.uniquindio.domain.dto.response.ExamenResponse;
import co.edu.uniquindio.service.interfaces.ExamenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class ExamenControllerTest {

    @Mock
    private ExamenService examenService;

    @InjectMocks
    private ExamenController examenController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(examenController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void obtenerExamenesPorPaciente_Successful_ReturnsListOfExamenes() throws Exception {
        // Given
        String pacienteId = "paciente123";

        ExamenResponse examen1 = createExamenResponse("examen1", "Hemograma", "SOLICITADO", false);
        ExamenResponse examen2 = createExamenResponse("examen2", "Radiografía", "COMPLETADO", true);

        List<ExamenResponse> examenes = Arrays.asList(examen1, examen2);

        when(examenService.obtenerExamenesPorPaciente(anyString())).thenReturn(examenes);

        // When & Then
        mockMvc.perform(get("/api/examenes/paciente/{pacienteId}", pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("examen1"))
                .andExpect(jsonPath("$[0].tipo").value("Hemograma"))
                .andExpect(jsonPath("$[0].estado").value("SOLICITADO"))
                .andExpect(jsonPath("$[0].urgencia").value(false))
                .andExpect(jsonPath("$[1].id").value("examen2"))
                .andExpect(jsonPath("$[1].tipo").value("Radiografía"))
                .andExpect(jsonPath("$[1].estado").value("COMPLETADO"))
                .andExpect(jsonPath("$[1].urgencia").value(true));
    }

    @Test
    void obtenerExamenesPorPaciente_EmptyList_ReturnsEmptyArray() throws Exception {
        // Given
        String pacienteId = "paciente123";
        List<ExamenResponse> examenes = Arrays.asList();

        when(examenService.obtenerExamenesPorPaciente(anyString())).thenReturn(examenes);

        // When & Then
        mockMvc.perform(get("/api/examenes/paciente/{pacienteId}", pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void obtenerExamenesPorPacienteYEstado_Successful_ReturnsFilteredExamenes() throws Exception {
        // Given
        String pacienteId = "paciente123";
        String estado = "SOLICITADO";

        ExamenResponse examen1 = createExamenResponse("examen1", "Hemograma", "SOLICITADO", false);
        ExamenResponse examen2 = createExamenResponse("examen2", "Glicemia", "SOLICITADO", true);

        List<ExamenResponse> examenes = Arrays.asList(examen1, examen2);

        when(examenService.obtenerExamenesPorPacienteYEstado(anyString(), anyString())).thenReturn(examenes);

        // When & Then
        mockMvc.perform(get("/api/examenes/paciente/{pacienteId}/estado/{estado}", pacienteId, estado))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].estado").value("SOLICITADO"))
                .andExpect(jsonPath("$[1].estado").value("SOLICITADO"));
    }

    @Test
    void obtenerExamenesPorPacienteYEstado_NoExamenesFound_ReturnsEmptyArray() throws Exception {
        // Given
        String pacienteId = "paciente123";
        String estado = "CANCELADO";
        List<ExamenResponse> examenes = Arrays.asList();

        when(examenService.obtenerExamenesPorPacienteYEstado(anyString(), anyString())).thenReturn(examenes);

        // When & Then
        mockMvc.perform(get("/api/examenes/paciente/{pacienteId}/estado/{estado}", pacienteId, estado))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void obtenerExamenesPorPacienteYTipo_Successful_ReturnsFilteredExamenes() throws Exception {
        // Given
        String pacienteId = "paciente123";
        String tipo = "Laboratorio";

        ExamenResponse examen1 = createExamenResponse("examen1", "Hemograma", "SOLICITADO", false);
        examen1.setLaboratorio("Laboratorio Central");
        ExamenResponse examen2 = createExamenResponse("examen2", "Glicemia", "COMPLETADO", false);
        examen2.setLaboratorio("Laboratorio Central");

        List<ExamenResponse> examenes = Arrays.asList(examen1, examen2);

        when(examenService.obtenerExamenesPorPacienteYTipo(anyString(), anyString())).thenReturn(examenes);

        // When & Then
        mockMvc.perform(get("/api/examenes/paciente/{pacienteId}/tipo/{tipo}", pacienteId, tipo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].laboratorio").value("Laboratorio Central"))
                .andExpect(jsonPath("$[1].laboratorio").value("Laboratorio Central"));
    }

    @Test
    void obtenerExamenesPorPacienteYTipo_NoExamenesFound_ReturnsEmptyArray() throws Exception {
        // Given
        String pacienteId = "paciente123";
        String tipo = "Radiología";
        List<ExamenResponse> examenes = Arrays.asList();

        when(examenService.obtenerExamenesPorPacienteYTipo(anyString(), anyString())).thenReturn(examenes);

        // When & Then
        mockMvc.perform(get("/api/examenes/paciente/{pacienteId}/tipo/{tipo}", pacienteId, tipo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void obtenerExamenPorId_Successful_ReturnsExamenResponse() throws Exception {
        // Given
        String examenId = "examen123";
        String pacienteId = "paciente123";

        ExamenResponse examen = createExamenResponse(examenId, "Hemograma Completo", "COMPLETADO", false);
        examen.setLaboratorio("Laboratorio Central");
        examen.setMedicoSolicitante("Dr. Juan Pérez");
        examen.setFechaRealizacion(LocalDateTime.now().minusDays(1));
        examen.setFechaResultado(LocalDateTime.now());

        Map<String, String> resultados = new HashMap<>();
        resultados.put("Hemoglobina", "14.5 g/dL");
        resultados.put("Hematocrito", "42%");
        resultados.put("Leucocitos", "7500/mm³");
        examen.setResultados(resultados);
        examen.setArchivo("hemograma_123.pdf");
        examen.setObservaciones("Valores dentro del rango normal");

        when(examenService.obtenerExamenPorId(anyString(), anyString())).thenReturn(examen);

        // When & Then
        mockMvc.perform(get("/api/examenes/{examenId}/paciente/{pacienteId}", examenId, pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(examenId))
                .andExpect(jsonPath("$.tipo").value("Hemograma Completo"))
                .andExpect(jsonPath("$.estado").value("COMPLETADO"))
                .andExpect(jsonPath("$.laboratorio").value("Laboratorio Central"))
                .andExpect(jsonPath("$.medicoSolicitante").value("Dr. Juan Pérez"))
                .andExpect(jsonPath("$.urgencia").value(false))
                .andExpect(jsonPath("$.archivo").value("hemograma_123.pdf"))
                .andExpect(jsonPath("$.observaciones").value("Valores dentro del rango normal"))
                .andExpect(jsonPath("$.resultados.Hemoglobina").value("14.5 g/dL"))
                .andExpect(jsonPath("$.resultados.Hematocrito").value("42%"))
                .andExpect(jsonPath("$.resultados.Leucocitos").value("7500/mm³"));
    }

    @Test
    void obtenerExamenPorId_UrgentExamen_ReturnsExamenResponseWithUrgency() throws Exception {
        // Given
        String examenId = "examen456";
        String pacienteId = "paciente123";

        ExamenResponse examen = createExamenResponse(examenId, "Electrocardiograma", "SOLICITADO", true);
        examen.setLaboratorio("Cardiología");
        examen.setMedicoSolicitante("Dr. María García");
        examen.setObservaciones("Paciente con dolor torácico");

        when(examenService.obtenerExamenPorId(anyString(), anyString())).thenReturn(examen);

        // When & Then
        mockMvc.perform(get("/api/examenes/{examenId}/paciente/{pacienteId}", examenId, pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(examenId))
                .andExpect(jsonPath("$.tipo").value("Electrocardiograma"))
                .andExpect(jsonPath("$.estado").value("SOLICITADO"))
                .andExpect(jsonPath("$.urgencia").value(true))
                .andExpect(jsonPath("$.observaciones").value("Paciente con dolor torácico"));
    }

    @Test
    void obtenerExamenesPorPaciente_MultipleEstados_ReturnsMixedExamenes() throws Exception {
        // Given
        String pacienteId = "paciente123";

        ExamenResponse examen1 = createExamenResponse("examen1", "Hemograma", "SOLICITADO", false);
        ExamenResponse examen2 = createExamenResponse("examen2", "Radiografía", "COMPLETADO", false);
        ExamenResponse examen3 = createExamenResponse("examen3", "Ecografía", "EN_PROCESO", true);

        List<ExamenResponse> examenes = Arrays.asList(examen1, examen2, examen3);

        when(examenService.obtenerExamenesPorPaciente(anyString())).thenReturn(examenes);

        // When & Then
        mockMvc.perform(get("/api/examenes/paciente/{pacienteId}", pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].estado").value("SOLICITADO"))
                .andExpect(jsonPath("$[1].estado").value("COMPLETADO"))
                .andExpect(jsonPath("$[2].estado").value("EN_PROCESO"))
                .andExpect(jsonPath("$[2].urgencia").value(true));
    }

    private ExamenResponse createExamenResponse(String id, String tipo, String estado, boolean urgencia) {
        ExamenResponse examen = new ExamenResponse();
        examen.setId(id);
        examen.setTipo(tipo);
        examen.setFechaSolicitud(LocalDateTime.now().minusDays(2));
        examen.setEstado(estado);
        examen.setUrgencia(urgencia);
        examen.setMedicoSolicitante("Dr. Test");
        examen.setLaboratorio("Laboratorio Test");
        return examen;
    }

    /**
     * Método main para ejecutar los tests directamente desde la clase
     * Uso: java ExamenControllerTest
     * Esta implementación es completamente autónoma y no requiere JUnit Platform
     */
    public static void main(String[] args) {
        System.out.println("=== Ejecutando ExamenControllerTest ===");
        System.out.println("Tests de Gestión de Exámenes - ExamenController");
        System.out.println();
        
        int testsEjecutados = 0;
        int testsExitosos = 0;
        int testsFallidos = 0;
        
        ExamenControllerTest testInstance = new ExamenControllerTest();
        
        // Lista de métodos de test a ejecutar
        String[] testMethods = {
            "obtenerExamenesPorPaciente_Successful_ReturnsListOfExamenes",
            "obtenerExamenesPorPaciente_EmptyList_ReturnsEmptyArray",
            "obtenerExamenesPorPacienteYEstado_Successful_ReturnsFilteredExamenes",
            "obtenerExamenesPorPacienteYEstado_NoExamenesFound_ReturnsEmptyArray",
            "obtenerExamenesPorPacienteYTipo_Successful_ReturnsFilteredExamenes",
            "obtenerExamenesPorPacienteYTipo_NoExamenesFound_ReturnsEmptyArray",
            "obtenerExamenPorId_Successful_ReturnsExamenResponse",
            "obtenerExamenPorId_UrgentExamen_ReturnsExamenResponseWithUrgency",
            "obtenerExamenesPorPaciente_MultipleEstados_ReturnsMixedExamenes"
        };
        
        for (String testMethod : testMethods) {
            testsEjecutados++;
            System.out.print("Ejecutando " + testMethod + "... ");
            
            try {
                // Usar reflexión para ejecutar cada método de test
                java.lang.reflect.Method method = ExamenControllerTest.class.getDeclaredMethod(testMethod);
                method.setAccessible(true);
                
                // Configurar el test antes de ejecutarlo
                testInstance.setUp();
                
                // Ejecutar el test
                method.invoke(testInstance);
                
                System.out.println("✅ PASÓ");
                testsExitosos++;
                
            } catch (Exception e) {
                System.out.println("❌ FALLÓ");
                System.out.println("   Error: " + e.getCause() != null ? e.getCause().getMessage() : e.getMessage());
                testsFallidos++;
            }
        }
        
        // Mostrar resumen de resultados
        System.out.println("\n=== Resumen de Tests ===");
        System.out.println("Tests ejecutados: " + testsEjecutados);
        System.out.println("Tests exitosos: " + testsExitosos);
        System.out.println("Tests fallidos: " + testsFallidos);
        
        if (testsFallidos == 0) {
            System.out.println("\n🎉 ¡TODOS los tests pasaron exitosamente!");
            System.out.println("✅ El ExamenController está funcionando correctamente.");
        } else {
            System.out.println("\n⚠️  Algunos tests fallaron.");
            System.out.println("🔧 Revisa los errores mostrados arriba.");
        }
        
        System.out.println("\n=== Información del Test ===");
        System.out.println("Clase: ExamenControllerTest");
        System.out.println("Endpoints: /api/examenes");
        System.out.println("Funcionalidad: Gestión de exámenes médicos");
        System.out.println("Operaciones: Consultar exámenes por paciente, estado, tipo e ID");
    }
}
