package co.edu.uniquindio.dto.cuenta;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CrearCuentaProfesionalDTO(
        @NotNull String tipoDocumento,
        @NotBlank @Length(max = 15) String nroDocumento,
        @NotBlank @Length(max = 10) String telefono,
        @NotBlank @Length(max = 15) String nombres,
        @NotBlank @Length(max = 15) String apellidos,
        @Length(max = 100) String direccion,
        @NotNull List<ObjectId> especialidades,
        @NotBlank @Length(max = 40) @Email String email,
        @NotBlank @Length(min = 7 , max = 20) String password) {
}
