
package co.edu.uniquindio.dto;

public record UsuarioDTO(

        String nombre,
        String identificacion,
        String telefono,
        String ciudad,
        String direccion,
        String email,
        String rol  // ← Nuevo campo
) {
}