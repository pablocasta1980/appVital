// src/main/java/co/edu/uniquindio/domain/dto/response/AuthResponse.java
package co.edu.uniquindio.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String tipoToken;
    private UsuarioResponse usuario;
}