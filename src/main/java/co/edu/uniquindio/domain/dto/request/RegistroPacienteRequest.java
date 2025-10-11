package co.edu.uniquindio.domain.dto.request;

import lombok.Data;

@Data
public class RegistroPacienteRequest {
    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private String ciudad;
    private String direccion;
}