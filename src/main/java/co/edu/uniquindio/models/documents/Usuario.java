package co.edu.uniquindio.models.documents;

import co.edu.uniquindio.models.enums.RolUsuario;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Usuario {

    @Id
    @EqualsAndHashCode.Include
    protected ObjectId codigo;

    // Cambiar de private a protected para que las clases hijas puedan acceder
    protected String identificacion;
    protected String nombre;
    protected String email;
    protected String telefono;
    protected String ciudad;
    protected String direccion;
    protected String password;
    protected RolUsuario rol;
}