package co.edu.uniquindio.models.documents;

import co.edu.uniquindio.models.enums.Consultorio;
import co.edu.uniquindio.models.enums.EstadoCita;
import co.edu.uniquindio.models.enums.EstadoRegistro;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Document("citas")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cita {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String codigo;
    private ObjectId idMedico;
    private ObjectId idPaciente;
    private ObjectId idSede;
    private boolean confirmada;
    private LocalDateTime fechaCita;
    private String especialidad;
    private String duracion;
    private String consultorio;
    private String comentarios;
    private EstadoCita estado;
    private String estadoRegistro;
    private ObjectId usuarioCreacion;
    private LocalDateTime fechaCreacion;

<<<<<<< Updated upstream
    private String pacienteId;
    private String doctorId;
    private LocalDateTime fechaHora;
    private String motivo;
}
=======
    public Cita(String codigo,
                ObjectId idMedico,
                ObjectId idPaciente,
                ObjectId idSede,
                boolean confirmada,
                LocalDateTime fechaCita,
                String especialidad,
                String duracion,
                String consultorio,
                String comentarios,
                EstadoCita estado,
                String estadoRegistro,
                ObjectId usuarioCreacion,
                LocalDateTime fechaCreacion) {
        this.codigo = codigo;
        this.idMedico = idMedico;
        this.idPaciente = idPaciente;
        this.idSede = idSede;
        this.confirmada = confirmada;
        this.fechaCita = fechaCita;
        this.especialidad = especialidad;
        this.duracion = duracion;
        this.consultorio = consultorio;
        this.comentarios = comentarios;
        this.estado = estado;
        this.estadoRegistro = estadoRegistro;
        this.usuarioCreacion = usuarioCreacion;
        this.fechaCreacion = fechaCreacion;
    }
}
>>>>>>> Stashed changes
