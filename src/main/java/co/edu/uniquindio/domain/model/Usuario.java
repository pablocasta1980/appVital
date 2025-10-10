// src/main/java/co/edu/uniquindio/domain/model/Usuario.java
package co.edu.uniquindio.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;
    private String nombre;
    private String telefono;
    private String ciudad;
    private String direccion;
    private String tipoUsuario;
    private String especialidad;
    private String numeroLicencia;
    private LocalDateTime fechaRegistro;
    private boolean activo;
}