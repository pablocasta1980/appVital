package co.edu.uniquindio.controller;

import co.edu.uniquindio.domain.dto.request.RegistroPacienteRequest;
import co.edu.uniquindio.domain.dto.response.UsuarioResponse;
import co.edu.uniquindio.service.interfaces.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistroController.class)
class RegistroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private RegistroPacienteRequest registroRequest;
    private UsuarioResponse usuarioResponse;

    @BeforeEach
    void setUp() {
        registroRequest = new RegistroPacienteRequest();
        registroRequest.setNombre("Juan Pérez");
        registroRequest.setEmail("juan.perez@email.com");
        registroRequest.setPassword("password123");
        registroRequest.setTelefono("3001234567");
        registroRequest.setCiudad("Armenia");
        registroRequest.setDireccion("Calle 123 #45-67");

        usuarioResponse = UsuarioResponse.builder()
                .id("usuario-123")
                .nombre("Juan Pérez")
                .email("juan.perez@email.com")
                .telefono("3001234567")
                .ciudad("Armenia")
                .direccion("Calle 123 #45-67")
                .tipoUsuario("PACIENTE")
                .build();
    }

    @Test
    void registrarPaciente_ShouldReturnCreatedUser() throws Exception {
        // Given
        when(usuarioService.registrarPaciente(any(RegistroPacienteRequest.class)))
                .thenReturn(usuarioResponse);

        // When & Then
        mockMvc.perform(post("/api/auth/registro/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("usuario-123"))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$.email").value("juan.perez@email.com"))
                .andExpect(jsonPath("$.telefono").value("3001234567"))
                .andExpect(jsonPath("$.ciudad").value("Armenia"))
                .andExpect(jsonPath("$.direccion").value("Calle 123 #45-67"))
                .andExpect(jsonPath("$.tipoUsuario").value("PACIENTE"));

        verify(usuarioService).registrarPaciente(any(RegistroPacienteRequest.class));
    }

    @Test
    void registrarPaciente_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Given
        RegistroPacienteRequest invalidRequest = new RegistroPacienteRequest();
        // Missing required fields

        // When & Then
        mockMvc.perform(post("/api/auth/registro/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());

        verify(usuarioService, never()).registrarPaciente(any(RegistroPacienteRequest.class));
    }

    @Test
    void registrarPaciente_WithEmptyRequestBody_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/auth/registro/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());

        verify(usuarioService, never()).registrarPaciente(any(RegistroPacienteRequest.class));
    }

    @Test
    void verificarEmail_WhenEmailExists_ShouldReturnTrue() throws Exception {
        // Given
        String email = "juan.perez@email.com";
        when(usuarioService.existeEmail(email)).thenReturn(true);

        // When & Then
        mockMvc.perform(get("/api/auth/verificar-email/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(true));

        verify(usuarioService).existeEmail(email);
    }

    @Test
    void verificarEmail_WhenEmailDoesNotExist_ShouldReturnFalse() throws Exception {
        // Given
        String email = "nuevo.usuario@email.com";
        when(usuarioService.existeEmail(email)).thenReturn(false);

        // When & Then
        mockMvc.perform(get("/api/auth/verificar-email/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(false));

        verify(usuarioService).existeEmail(email);
    }

    @Test
    void verificarEmail_WithSpecialCharacters_ShouldHandleCorrectly() throws Exception {
        // Given
        String email = "usuario+test@email.com";
        when(usuarioService.existeEmail(email)).thenReturn(false);

        // When & Then
        mockMvc.perform(get("/api/auth/verificar-email/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(false));

        verify(usuarioService).existeEmail(email);
    }

    @Test
    void verificarEmail_WithEmptyEmail_ShouldReturnFalse() throws Exception {
        // Given
        String email = "";
        when(usuarioService.existeEmail(email)).thenReturn(false);

        // When & Then
        mockMvc.perform(get("/api/auth/verificar-email/{email}", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(false));

        verify(usuarioService).existeEmail(email);
    }
}
