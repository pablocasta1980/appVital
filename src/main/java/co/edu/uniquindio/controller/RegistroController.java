package co.edu.uniquindio.controller;

import co.edu.uniquindio.domain.dto.request.RegistroPacienteRequest;
import co.edu.uniquindio.domain.dto.response.UsuarioResponse;
import co.edu.uniquindio.service.interfaces.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Registro", description = "Endpoints para registro de usuarios")
public class RegistroController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Registrar nuevo paciente", description = "Crea una nueva cuenta de paciente")
    @PostMapping("/registro/paciente")
    public ResponseEntity<UsuarioResponse> registrarPaciente(
            @RequestBody RegistroPacienteRequest request) {
        UsuarioResponse usuario = usuarioService.registrarPaciente(request);
        return ResponseEntity.ok(usuario);
    }

    @Operation(summary = "Verificar email", description = "Verifica si un email ya está registrado")
    @GetMapping("/verificar-email/{email}")
    public ResponseEntity<Boolean> verificarEmail(@PathVariable String email) {
        boolean existe = usuarioService.existeEmail(email);
        return ResponseEntity.ok(existe);
    }
}