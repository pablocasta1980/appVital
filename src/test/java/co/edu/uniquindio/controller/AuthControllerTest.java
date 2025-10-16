package co.edu.uniquindio.controller;

import co.edu.uniquindio.domain.dto.request.LoginRequest;
import co.edu.uniquindio.domain.dto.response.AuthResponse;
import co.edu.uniquindio.domain.dto.response.UsuarioResponse;
import co.edu.uniquindio.service.interfaces.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void login_Successful_ReturnsAuthResponse() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        UsuarioResponse usuarioResponse = UsuarioResponse.builder()
                .id("1")
                .nombre("Juan Pérez")
                .email("test@example.com")
                .telefono("1234567890")
                .ciudad("Armenia")
                .direccion("Calle 123")
                .tipoUsuario("PACIENTE")
                .build();

        AuthResponse authResponse = AuthResponse.builder()
                .token("jwt-token-here")
                .tipoToken("Bearer")
                .usuario(usuarioResponse)
                .build();

        when(authService.login(any(LoginRequest.class))).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("jwt-token-here"))
                .andExpect(jsonPath("$.tipoToken").value("Bearer"))
                .andExpect(jsonPath("$.usuario.id").value("1"))
                .andExpect(jsonPath("$.usuario.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.usuario.email").value("test@example.com"))
                .andExpect(jsonPath("$.usuario.tipoUsuario").value("PACIENTE"));
    }

    @Test
    void login_InvalidEmail_ReturnsBadRequest() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("invalid-email");
        loginRequest.setPassword("password123");

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_BlankEmail_ReturnsBadRequest() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("");
        loginRequest.setPassword("password123");

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_BlankPassword_ReturnsBadRequest() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("");

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_NullEmail_ReturnsBadRequest() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(null);
        loginRequest.setPassword("password123");

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_NullPassword_ReturnsBadRequest() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword(null);

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_EmptyRequestBody_ReturnsBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_InvalidJson_ReturnsBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("invalid json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_MedicoUser_ReturnsAuthResponseWithEspecialidad() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("doctor@example.com");
        loginRequest.setPassword("password123");

        UsuarioResponse usuarioResponse = UsuarioResponse.builder()
                .id("2")
                .nombre("Dr. María García")
                .email("doctor@example.com")
                .telefono("0987654321")
                .ciudad("Armenia")
                .direccion("Calle 456")
                .tipoUsuario("MEDICO")
                .especialidad("Cardiología")
                .build();

        AuthResponse authResponse = AuthResponse.builder()
                .token("jwt-token-medico")
                .tipoToken("Bearer")
                .usuario(usuarioResponse)
                .build();

        when(authService.login(any(LoginRequest.class))).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("jwt-token-medico"))
                .andExpect(jsonPath("$.tipoToken").value("Bearer"))
                .andExpect(jsonPath("$.usuario.id").value("2"))
                .andExpect(jsonPath("$.usuario.nombre").value("Dr. María García"))
                .andExpect(jsonPath("$.usuario.email").value("doctor@example.com"))
                .andExpect(jsonPath("$.usuario.tipoUsuario").value("MEDICO"))
                .andExpect(jsonPath("$.usuario.especialidad").value("Cardiología"));
    }

    /**
     * Método main para ejecutar los tests directamente desde la clase
     * Uso: java AuthControllerTest
     * Esta implementación es completamente autónoma y no requiere JUnit Platform
     */
    public static void main(String[] args) {
        System.out.println("=== Ejecutando AuthControllerTest ===");
        System.out.println("Tests de Autenticación - AuthController");
        System.out.println();
        
        int testsEjecutados = 0;
        int testsExitosos = 0;
        int testsFallidos = 0;
        
        AuthControllerTest testInstance = new AuthControllerTest();
        
        // Lista de métodos de test a ejecutar
        String[] testMethods = {
            "login_Successful_ReturnsAuthResponse",
            "login_InvalidEmail_ReturnsBadRequest", 
            "login_BlankEmail_ReturnsBadRequest",
            "login_BlankPassword_ReturnsBadRequest",
            "login_NullEmail_ReturnsBadRequest",
            "login_NullPassword_ReturnsBadRequest",
            "login_EmptyRequestBody_ReturnsBadRequest",
            "login_InvalidJson_ReturnsBadRequest",
            "login_MedicoUser_ReturnsAuthResponseWithEspecialidad"
        };
        
        for (String testMethod : testMethods) {
            testsEjecutados++;
            System.out.print("Ejecutando " + testMethod + "... ");
            
            try {
                // Usar reflexión para ejecutar cada método de test
                java.lang.reflect.Method method = AuthControllerTest.class.getDeclaredMethod(testMethod);
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
            System.out.println("✅ El AuthController está funcionando correctamente.");
        } else {
            System.out.println("\n⚠️  Algunos tests fallaron.");
            System.out.println("🔧 Revisa los errores mostrados arriba.");
        }
        
        System.out.println("\n=== Información del Test ===");
        System.out.println("Clase: AuthControllerTest");
        System.out.println("Endpoint: /api/auth/login");
        System.out.println("Funcionalidad: Autenticación de usuarios");
    }
}
