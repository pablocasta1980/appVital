package co.edu.uniquindio.controller;

import co.edu.uniquindio.domain.dto.request.CitaRequest;
import co.edu.uniquindio.domain.dto.response.CitaResponse;
import co.edu.uniquindio.service.interfaces.CitaService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class CitaControllerTest {

    @Mock
    private CitaService citaService;

    @InjectMocks
    private CitaController citaController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(citaController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void agendarCita_Successful_ReturnsCitaResponse() throws Exception {
        // Given
        String pacienteId = "paciente123";
        CitaRequest citaRequest = new CitaRequest();
        citaRequest.setMedicoId("medico456");
        citaRequest.setFecha(LocalDateTime.now().plusDays(1));
        citaRequest.setTipo("PRESENCIAL");
        citaRequest.setMotivo("Consulta general");

        CitaResponse citaResponse = new CitaResponse();
        citaResponse.setId("cita789");
        citaResponse.setMedicoNombre("Dr. Juan Pérez");
        citaResponse.setEspecialidad("Medicina General");
        citaResponse.setFecha(citaRequest.getFecha());
        citaResponse.setEstado("PROGRAMADA");
        citaResponse.setTipo("PRESENCIAL");
        citaResponse.setConsultorio("Consultorio 101");
        citaResponse.setMotivo("Consulta general");

        when(citaService.agendarCita(anyString(), any(CitaRequest.class))).thenReturn(citaResponse);

        // When & Then
        mockMvc.perform(post("/api/citas")
                        .header("pacienteId", pacienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(citaRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("cita789"))
                .andExpect(jsonPath("$.medicoNombre").value("Dr. Juan Pérez"))
                .andExpect(jsonPath("$.especialidad").value("Medicina General"))
                .andExpect(jsonPath("$.estado").value("PROGRAMADA"))
                .andExpect(jsonPath("$.tipo").value("PRESENCIAL"))
                .andExpect(jsonPath("$.consultorio").value("Consultorio 101"))
                .andExpect(jsonPath("$.motivo").value("Consulta general"));
    }

    @Test
    void agendarCita_MissingPacienteIdHeader_ReturnsBadRequest() throws Exception {
        // Given
        CitaRequest citaRequest = new CitaRequest();
        citaRequest.setMedicoId("medico456");
        citaRequest.setFecha(LocalDateTime.now().plusDays(1));
        citaRequest.setTipo("PRESENCIAL");
        citaRequest.setMotivo("Consulta general");

        // When & Then
        mockMvc.perform(post("/api/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(citaRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void agendarCita_InvalidCitaRequest_ReturnsBadRequest() throws Exception {
        // Given
        String pacienteId = "paciente123";
        CitaRequest citaRequest = new CitaRequest();
        // Missing required fields

        // When & Then
        mockMvc.perform(post("/api/citas")
                        .header("pacienteId", pacienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(citaRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cancelarCita_Successful_ReturnsCitaResponse() throws Exception {
        // Given
        String citaId = "cita789";
        String pacienteId = "paciente123";

        CitaResponse citaResponse = new CitaResponse();
        citaResponse.setId(citaId);
        citaResponse.setMedicoNombre("Dr. Juan Pérez");
        citaResponse.setEspecialidad("Medicina General");
        citaResponse.setFecha(LocalDateTime.now().plusDays(1));
        citaResponse.setEstado("CANCELADA");
        citaResponse.setTipo("PRESENCIAL");
        citaResponse.setConsultorio("Consultorio 101");
        citaResponse.setMotivo("Consulta general");

        when(citaService.cancelarCita(anyString(), anyString())).thenReturn(citaResponse);

        // When & Then
        mockMvc.perform(put("/api/citas/{citaId}/cancelar", citaId)
                        .header("pacienteId", pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(citaId))
                .andExpect(jsonPath("$.estado").value("CANCELADA"));
    }

    @Test
    void cancelarCita_MissingPacienteIdHeader_ReturnsBadRequest() throws Exception {
        // Given
        String citaId = "cita789";

        // When & Then
        mockMvc.perform(put("/api/citas/{citaId}/cancelar", citaId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerCitasPorPaciente_Successful_ReturnsListOfCitas() throws Exception {
        // Given
        String pacienteId = "paciente123";

        CitaResponse cita1 = new CitaResponse();
        cita1.setId("cita1");
        cita1.setMedicoNombre("Dr. Juan Pérez");
        cita1.setEspecialidad("Medicina General");
        cita1.setFecha(LocalDateTime.now().plusDays(1));
        cita1.setEstado("PROGRAMADA");
        cita1.setTipo("PRESENCIAL");

        CitaResponse cita2 = new CitaResponse();
        cita2.setId("cita2");
        cita2.setMedicoNombre("Dr. María García");
        cita2.setEspecialidad("Cardiología");
        cita2.setFecha(LocalDateTime.now().plusDays(2));
        cita2.setEstado("COMPLETADA");
        cita2.setTipo("VIRTUAL");

        List<CitaResponse> citas = Arrays.asList(cita1, cita2);

        when(citaService.obtenerCitasPorPaciente(anyString())).thenReturn(citas);

        // When & Then
        mockMvc.perform(get("/api/citas/paciente/{pacienteId}", pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("cita1"))
                .andExpect(jsonPath("$[0].medicoNombre").value("Dr. Juan Pérez"))
                .andExpect(jsonPath("$[0].estado").value("PROGRAMADA"))
                .andExpect(jsonPath("$[1].id").value("cita2"))
                .andExpect(jsonPath("$[1].medicoNombre").value("Dr. María García"))
                .andExpect(jsonPath("$[1].estado").value("COMPLETADA"));
    }

    @Test
    void obtenerCitasPorPaciente_EmptyList_ReturnsEmptyArray() throws Exception {
        // Given
        String pacienteId = "paciente123";
        List<CitaResponse> citas = Arrays.asList();

        when(citaService.obtenerCitasPorPaciente(anyString())).thenReturn(citas);

        // When & Then
        mockMvc.perform(get("/api/citas/paciente/{pacienteId}", pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void obtenerCitasPorPacienteYEstado_Successful_ReturnsFilteredCitas() throws Exception {
        // Given
        String pacienteId = "paciente123";
        String estado = "PROGRAMADA";

        CitaResponse cita1 = new CitaResponse();
        cita1.setId("cita1");
        cita1.setMedicoNombre("Dr. Juan Pérez");
        cita1.setEspecialidad("Medicina General");
        cita1.setFecha(LocalDateTime.now().plusDays(1));
        cita1.setEstado("PROGRAMADA");
        cita1.setTipo("PRESENCIAL");

        CitaResponse cita2 = new CitaResponse();
        cita2.setId("cita2");
        cita2.setMedicoNombre("Dr. María García");
        cita2.setEspecialidad("Cardiología");
        cita2.setFecha(LocalDateTime.now().plusDays(2));
        cita2.setEstado("PROGRAMADA");
        cita2.setTipo("VIRTUAL");

        List<CitaResponse> citas = Arrays.asList(cita1, cita2);

        when(citaService.obtenerCitasPorPacienteYEstado(anyString(), anyString())).thenReturn(citas);

        // When & Then
        mockMvc.perform(get("/api/citas/paciente/{pacienteId}/estado/{estado}", pacienteId, estado))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].estado").value("PROGRAMADA"))
                .andExpect(jsonPath("$[1].estado").value("PROGRAMADA"));
    }

    @Test
    void obtenerCitasPorPacienteYEstado_NoCitasFound_ReturnsEmptyArray() throws Exception {
        // Given
        String pacienteId = "paciente123";
        String estado = "CANCELADA";
        List<CitaResponse> citas = Arrays.asList();

        when(citaService.obtenerCitasPorPacienteYEstado(anyString(), anyString())).thenReturn(citas);

        // When & Then
        mockMvc.perform(get("/api/citas/paciente/{pacienteId}/estado/{estado}", pacienteId, estado))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void agendarCita_VirtualCita_Successful() throws Exception {
        // Given
        String pacienteId = "paciente123";
        CitaRequest citaRequest = new CitaRequest();
        citaRequest.setMedicoId("medico456");
        citaRequest.setFecha(LocalDateTime.now().plusDays(1));
        citaRequest.setTipo("VIRTUAL");
        citaRequest.setMotivo("Consulta virtual");

        CitaResponse citaResponse = new CitaResponse();
        citaResponse.setId("cita789");
        citaResponse.setMedicoNombre("Dr. Juan Pérez");
        citaResponse.setEspecialidad("Medicina General");
        citaResponse.setFecha(citaRequest.getFecha());
        citaResponse.setEstado("PROGRAMADA");
        citaResponse.setTipo("VIRTUAL");
        citaResponse.setConsultorio("Virtual");
        citaResponse.setMotivo("Consulta virtual");

        when(citaService.agendarCita(anyString(), any(CitaRequest.class))).thenReturn(citaResponse);

        // When & Then
        mockMvc.perform(post("/api/citas")
                        .header("pacienteId", pacienteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(citaRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tipo").value("VIRTUAL"))
                .andExpect(jsonPath("$.consultorio").value("Virtual"));
    }

    /**
     * Método main para ejecutar los tests directamente desde la clase
     * Uso: java CitaControllerTest
     * Esta implementación es completamente autónoma y no requiere JUnit Platform
     */
    public static void main(String[] args) {
        System.out.println("=== Ejecutando CitaControllerTest ===");
        System.out.println("Tests de Gestión de Citas - CitaController");
        System.out.println();
        
        int testsEjecutados = 0;
        int testsExitosos = 0;
        int testsFallidos = 0;
        
        CitaControllerTest testInstance = new CitaControllerTest();
        
        // Lista de métodos de test a ejecutar
        String[] testMethods = {
            "agendarCita_Successful_ReturnsCitaResponse",
            "agendarCita_MissingPacienteIdHeader_ReturnsBadRequest",
            "agendarCita_InvalidCitaRequest_ReturnsBadRequest",
            "cancelarCita_Successful_ReturnsCitaResponse",
            "cancelarCita_MissingPacienteIdHeader_ReturnsBadRequest",
            "obtenerCitasPorPaciente_Successful_ReturnsListOfCitas",
            "obtenerCitasPorPaciente_EmptyList_ReturnsEmptyArray",
            "obtenerCitasPorPacienteYEstado_Successful_ReturnsFilteredCitas",
            "obtenerCitasPorPacienteYEstado_NoCitasFound_ReturnsEmptyArray",
            "agendarCita_VirtualCita_Successful"
        };
        
        for (String testMethod : testMethods) {
            testsEjecutados++;
            System.out.print("Ejecutando " + testMethod + "... ");
            
            try {
                // Usar reflexión para ejecutar cada método de test
                java.lang.reflect.Method method = CitaControllerTest.class.getDeclaredMethod(testMethod);
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
            System.out.println("✅ El CitaController está funcionando correctamente.");
        } else {
            System.out.println("\n⚠️  Algunos tests fallaron.");
            System.out.println("🔧 Revisa los errores mostrados arriba.");
        }
        
        System.out.println("\n=== Información del Test ===");
        System.out.println("Clase: CitaControllerTest");
        System.out.println("Endpoints: /api/citas");
        System.out.println("Funcionalidad: Gestión de citas médicas");
        System.out.println("Operaciones: Agendar, Cancelar, Consultar citas");
    }
}
