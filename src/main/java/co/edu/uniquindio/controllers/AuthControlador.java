package co.edu.uniquindio.controllers;

import co.edu.uniquindio.dto.MensajeDTO;
import co.edu.uniquindio.dto.auth.ConfirmacionDTO;
import co.edu.uniquindio.dto.auth.LoginDTO;
import co.edu.uniquindio.dto.auth.TokenDTO;
import co.edu.uniquindio.servicios.interfaces.AuthServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControlador {

    private final AuthServicio authServicio;

    @PostMapping("/login")
    public ResponseEntity<MensajeDTO<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        TokenDTO token = authServicio.login(loginDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, token));
    }

    // Confirmar cuenta (POST - para aplicaciones/Swagger)
    @PostMapping("/confirmar")
    public ResponseEntity<MensajeDTO<String>> confirmarCuenta(@Valid @RequestBody ConfirmacionDTO confirmacionDTO) throws Exception {
        authServicio.confirmarCuenta(confirmacionDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "✅ Cuenta confirmada exitosamente. Ya puedes iniciar sesión."));
    }

    // Confirmar cuenta (GET - para enlaces de email)
    @GetMapping("/confirmar")
    public ResponseEntity<MensajeDTO<String>> confirmarCuentaGet(@RequestParam String token) throws Exception {
        ConfirmacionDTO confirmacionDTO = new ConfirmacionDTO(token);
        authServicio.confirmarCuenta(confirmacionDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, "✅ Cuenta confirmada exitosamente. Ya puedes iniciar sesión."));
    }

}