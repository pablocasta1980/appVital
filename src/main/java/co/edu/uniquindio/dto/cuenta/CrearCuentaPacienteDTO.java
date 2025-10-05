package co.edu.uniquindio.dto.cuenta;

import co.edu.uniquindio.models.enums.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record CrearCuentaPacienteDTO(
        @NotBlank String tipoDocumento,
        @NotBlank @Length(max = 15) String nroDocumento,
        @NotBlank @Length(max = 10) String telefono,
        @NotBlank @Length(max = 15) String nombres,
        @NotBlank @Length(max = 15) String apellidos,
        @Length(max = 100) String direccion,
        @NotNull Nacionalidad nacionalidad,
        @NotNull
        @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
        LocalDate fechaNacimiento,
        @NotNull String departamento,
        @NotNull String ciudad,
        @NotNull String celular,
        @NotNull Regimen regimen,
        @NotNull String planComplementario,
        @NotBlank @Length(max = 40) @Email String email,
        @NotBlank @Length(min = 7 , max = 20) String password) {
}
