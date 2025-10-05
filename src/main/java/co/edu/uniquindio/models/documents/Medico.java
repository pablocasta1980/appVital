package co.edu.uniquindio.models.documents;

import co.edu.uniquindio.models.enums.Especialidad;
import co.edu.uniquindio.models.enums.EstadoUsuario;
import co.edu.uniquindio.models.enums.RolUsuario;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@Document("medicos")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Medico extends Usuario {

    // Campos específicos de médico
    private Especialidad especialidad;
    private String registroMedico;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Integer añosExperiencia;
    private Double calificacion; // 1.0 - 5.0
    private String consultorio;

    // NUEVO: Estado profesional (diferente del estado de cuenta)
    private Boolean estadoProfesional; // true = activo, false = inactivo

    // Constructor específico para Médico
    public Medico(String identificacion, String nombre, String email, String telefono,
                  String ciudad, String direccion, String password, Especialidad especialidad) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.password = password;
        this.especialidad = especialidad;
        this.rol = RolUsuario.MEDICO;
        this.estado = EstadoUsuario.INACTIVO; // Estado de cuenta (de la clase Usuario)
        this.estadoProfesional = true; // Estado profesional (activo por defecto)
        this.calificacion = 0.0; // Calificación inicial
    }
}