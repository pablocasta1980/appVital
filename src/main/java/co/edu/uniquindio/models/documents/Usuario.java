package co.edu.uniquindio.models.documents;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document("usuarios")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId codigo;

    private String cedula;
    private String identificacion;
    private String nombre;
    private String email;
    private String telefono;
    private String ciudad;
    private String direccion;
    private String password;


}


