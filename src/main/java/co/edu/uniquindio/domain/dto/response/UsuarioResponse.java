// src/main/java/co/edu/uniquindio/domain/dto/response/UsuarioResponse.java
package co.edu.uniquindio.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private String ciudad;
    private String direccion;
    private String tipoUsuario;
    private String especialidad; // Solo para médicos
}