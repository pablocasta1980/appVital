package co.edu.uniquindio.models.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("sedes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sede {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String nombre;
    private String departamento;
    private String ciudad;
    private String direccion;
    private String telefono;
}
