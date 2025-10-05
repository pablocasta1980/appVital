package co.edu.uniquindio.dto.cuenta;

import co.edu.uniquindio.models.enums.TipoDocumento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record EditarCuentaPacienteDTO(
        @NotBlank String id,
        @NotNull TipoDocumento tipoDocumento,
        @NotBlank @Length(max = 10) String nroDocumento,
        @NotBlank @Length(max = 10) String telefono,
        @Length(max = 100) String direccion,
        @NotBlank @Length(max = 40) @Email String email,
        @NotBlank @Length(min = 7 , max = 20) String password) {
}
