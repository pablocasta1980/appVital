package co.edu.uniquindio.controllers;

import co.edu.uniquindio.dto.LoginDTO;
import co.edu.uniquindio.dto.MensajeDTO;
import co.edu.uniquindio.dto.TokenDTO;
import co.edu.uniquindio.servicios.interfaces.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControlador {

    private final UsuarioServicio usuarioServicio;

    @PostMapping("/login")
    public ResponseEntity<MensajeDTO<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO)
            throws Exception {
        TokenDTO token = usuarioServicio.login(loginDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, token));
    }
}
