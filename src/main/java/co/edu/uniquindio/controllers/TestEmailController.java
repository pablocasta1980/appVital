package co.edu.uniquindio.controllers;

import co.edu.uniquindio.dto.EmailDTO;
import co.edu.uniquindio.servicios.interfaces.EmailServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-email")
@RequiredArgsConstructor
public class TestEmailController {

    private final EmailServicio emailServicio;

    @PostMapping
    public String testEmail() {
        try {
            EmailDTO email = new EmailDTO(
                    "Prueba de correo - Spring Boot Mail",
                    "¡Hola! Este es un correo de prueba enviado desde Spring Boot con Gmail.",
                    "pabloandrescasta@hotmail.com" // Cambia por tu correo real
            );
            emailServicio.enviarCorreo(email);
            return "✅ Correo enviado exitosamente";
        } catch (Exception e) {
            return "❌ Error enviando correo: " + e.getMessage();
        }
    }
}