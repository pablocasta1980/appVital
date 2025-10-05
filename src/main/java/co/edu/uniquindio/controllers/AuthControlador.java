package co.edu.uniquindio.controllers;

import co.edu.uniquindio.dto.MensajeDTO;
import co.edu.uniquindio.dto.auth.LoginDTO;
import co.edu.uniquindio.dto.auth.TokenDTO;
import co.edu.uniquindio.servicios.interfaces.AuthServicio;
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

    private final AuthServicio authServicio;

    @PostMapping("/login")
    public ResponseEntity<MensajeDTO<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        TokenDTO token = authServicio.login(loginDTO);
        return ResponseEntity.ok(new MensajeDTO<>(false, token));
    }
}