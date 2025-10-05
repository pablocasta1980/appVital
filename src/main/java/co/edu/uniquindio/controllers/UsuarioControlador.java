package co.edu.uniquindio.controllers;


import co.edu.uniquindio.dto.CrearUsuarioDTO;
import co.edu.uniquindio.dto.EditarUsuarioDTO;
import co.edu.uniquindio.dto.MensajeDTO;
import co.edu.uniquindio.dto.UsuarioDTO;
import co.edu.uniquindio.servicios.interfaces.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;


    @PostMapping
    public ResponseEntity<MensajeDTO<String>> crear(@Valid @RequestBody CrearUsuarioDTO cuenta) throws Exception{
        usuarioServicio.crear(cuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Su registro ha sido exitoso"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> editar(@PathVariable String id,@Valid @RequestBody EditarUsuarioDTO cuenta) throws Exception{
        usuarioServicio.editar(id,cuenta);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta editada exitosamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminar(@PathVariable String id) throws Exception{
        usuarioServicio.eliminar(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta eliminada exitosamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<UsuarioDTO>> obtener(@PathVariable String id) throws Exception{
        UsuarioDTO info = usuarioServicio.obtener(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, info));
    }

    @DeleteMapping("/identificacion/{identificacion}")
    public ResponseEntity<MensajeDTO<String>> eliminarPorIdentificacion(
            @PathVariable String identificacion) throws Exception {
        usuarioServicio.eliminarPorIdentificacion(identificacion);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Cuenta eliminada exitosamente por identificación"));
    }

    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<MensajeDTO<UsuarioDTO>> obtenerPorIdentificacion(
            @PathVariable String identificacion) throws Exception {
        UsuarioDTO info = usuarioServicio.obtenerPorIdentificacion(identificacion);
        return ResponseEntity.ok(new MensajeDTO<>(false, info));
    }



}
