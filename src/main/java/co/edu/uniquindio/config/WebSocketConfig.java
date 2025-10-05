package co.edu.uniquindio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Prefijo que usará el cliente para suscribirse a canales (por ejemplo: /topic/notificaciones)
        registry.enableSimpleBroker("/topic");

        // Prefijo que debe usar el cliente para enviar mensajes al servidor (por ejemplo: /app/enviar)
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // URL base del WebSocket (por ejemplo: ws://localhost:8080/ws)
                .setAllowedOriginPatterns("*") // Permitir todas las solicitudes de origen (útil en desarrollo)
                .withSockJS(); // Habilita fallback para navegadores antiguos
    }
}