package co.edu.uniquindio.models.documents;

import co.edu.uniquindio.models.enums.RolUsuario;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("pacientes")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Paciente extends Usuario {

    // Campos específicos de paciente
    private LocalDate fechaNacimiento;
    private String alergias;
    private String eps;
    private String tipoSangre;
    private Double peso; // en kg
    private Double altura; // en cm
    private String contactoEmergencia;
    private String telefonoEmergencia;

    // Constructor específico para Paciente
    public Paciente(String identificacion, String nombre, String email, String telefono,
                    String ciudad, String direccion, String password) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.password = password;
        this.rol = RolUsuario.PACIENTE;
    }
}