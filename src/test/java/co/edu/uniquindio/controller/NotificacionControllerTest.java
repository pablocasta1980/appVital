package co.edu.uniquindio.controller;

import co.edu.uniquindio.domain.dto.response.NotificacionResponse;
import co.edu.uniquindio.service.interfaces.NotificacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificacionController.class)
class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificacionService notificacionService;


    private NotificacionResponse notificacionResponse;
    private List<NotificacionResponse> notificacionesList;

    @BeforeEach
    void setUp() {
        notificacionResponse = NotificacionResponse.builder()
                .id("notif-123")
                .titulo("Recordatorio de cita")
                .mensaje("Tienes una cita mañana a las 10:00 AM")
                .tipo("CITA")
                .fechaCreacion(LocalDateTime.now())
                .leida(false)
                .urgente(false)
                .relacionId("cita-456")
                .relacionTipo("CITA")
                .build();

        notificacionesList = Arrays.asList(notificacionResponse);
    }

    @Test
    void obtenerNotificacionesPorPaciente_ShouldReturnListOfNotifications() throws Exception {
        // Given
        String pacienteId = "paciente-123";
        when(notificacionService.obtenerNotificacionesPorPaciente(pacienteId))
                .thenReturn(notificacionesList);

        // When & Then
        mockMvc.perform(get("/api/notificaciones/paciente/{pacienteId}", pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("notif-123"))
                .andExpect(jsonPath("$[0].titulo").value("Recordatorio de cita"))
                .andExpect(jsonPath("$[0].tipo").value("CITA"));

        verify(notificacionService).obtenerNotificacionesPorPaciente(pacienteId);
    }

    @Test
    void obtenerNotificacionesPorPaciente_ShouldReturnEmptyList() throws Exception {
        // Given
        String pacienteId = "paciente-123";
        when(notificacionService.obtenerNotificacionesPorPaciente(pacienteId))
                .thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("/api/notificaciones/paciente/{pacienteId}", pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(notificacionService).obtenerNotificacionesPorPaciente(pacienteId);
    }

    @Test
    void obtenerNotificacionesNoLeidas_ShouldReturnUnreadNotifications() throws Exception {
        // Given
        String pacienteId = "paciente-123";
        when(notificacionService.obtenerNotificacionesNoLeidas(pacienteId))
                .thenReturn(notificacionesList);

        // When & Then
        mockMvc.perform(get("/api/notificaciones/paciente/{pacienteId}/no-leidas", pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].leida").value(false));

        verify(notificacionService).obtenerNotificacionesNoLeidas(pacienteId);
    }

    @Test
    void obtenerNotificacionesPorTipo_ShouldReturnNotificationsByType() throws Exception {
        // Given
        String pacienteId = "paciente-123";
        String tipo = "CITA";
        when(notificacionService.obtenerNotificacionesPorTipo(pacienteId, tipo))
                .thenReturn(notificacionesList);

        // When & Then
        mockMvc.perform(get("/api/notificaciones/paciente/{pacienteId}/tipo/{tipo}", pacienteId, tipo))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].tipo").value("CITA"));

        verify(notificacionService).obtenerNotificacionesPorTipo(pacienteId, tipo);
    }

    @Test
    void marcarComoLeida_ShouldMarkNotificationAsRead() throws Exception {
        // Given
        String notificacionId = "notif-123";
        String pacienteId = "paciente-123";
        NotificacionResponse notificacionLeida = NotificacionResponse.builder()
                .id(notificacionId)
                .titulo("Recordatorio de cita")
                .mensaje("Tienes una cita mañana a las 10:00 AM")
                .tipo("CITA")
                .fechaCreacion(LocalDateTime.now())
                .fechaLeida(LocalDateTime.now())
                .leida(true)
                .urgente(false)
                .relacionId("cita-456")
                .relacionTipo("CITA")
                .build();

        when(notificacionService.marcarComoLeida(notificacionId, pacienteId))
                .thenReturn(notificacionLeida);

        // When & Then
        mockMvc.perform(put("/api/notificaciones/{notificacionId}/marcar-leida/paciente/{pacienteId}", 
                        notificacionId, pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(notificacionId))
                .andExpect(jsonPath("$.leida").value(true))
                .andExpect(jsonPath("$.fechaLeida").exists());

        verify(notificacionService).marcarComoLeida(notificacionId, pacienteId);
    }

    @Test
    void marcarTodasComoLeidas_ShouldMarkAllNotificationsAsRead() throws Exception {
        // Given
        String pacienteId = "paciente-123";
        doNothing().when(notificacionService).marcarTodasComoLeidas(pacienteId);

        // When & Then
        mockMvc.perform(put("/api/notificaciones/marcar-todas-leidas/paciente/{pacienteId}", pacienteId))
                .andExpect(status().isOk());

        verify(notificacionService).marcarTodasComoLeidas(pacienteId);
    }

    @Test
    void contarNotificacionesNoLeidas_ShouldReturnCountOfUnreadNotifications() throws Exception {
        // Given
        String pacienteId = "paciente-123";
        long expectedCount = 3L;
        when(notificacionService.contarNotificacionesNoLeidas(pacienteId))
                .thenReturn(expectedCount);

        // When & Then
        mockMvc.perform(get("/api/notificaciones/contar-no-leidas/paciente/{pacienteId}", pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(3));

        verify(notificacionService).contarNotificacionesNoLeidas(pacienteId);
    }

    @Test
    void marcarComoNoLeida_ShouldMarkNotificationAsUnread() throws Exception {
        // Given
        String notificacionId = "notif-123";
        String pacienteId = "paciente-123";
        NotificacionResponse notificacionNoLeida = NotificacionResponse.builder()
                .id(notificacionId)
                .titulo("Recordatorio de cita")
                .mensaje("Tienes una cita mañana a las 10:00 AM")
                .tipo("CITA")
                .fechaCreacion(LocalDateTime.now())
                .leida(false)
                .urgente(false)
                .relacionId("cita-456")
                .relacionTipo("CITA")
                .build();

        when(notificacionService.marcarComoNoLeida(notificacionId, pacienteId))
                .thenReturn(notificacionNoLeida);

        // When & Then
        mockMvc.perform(put("/api/notificaciones/{notificacionId}/marcar-no-leida/paciente/{pacienteId}", 
                        notificacionId, pacienteId))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(notificacionId))
                .andExpect(jsonPath("$.leida").value(false))
                .andExpect(jsonPath("$.fechaLeida").doesNotExist());

        verify(notificacionService).marcarComoNoLeida(notificacionId, pacienteId);
    }
}
