package co.edu.uniquindio.models.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("especialidades")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Especialidad {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String nombre;

    public Especialidad(String nombre) {
        this.nombre = nombre;
    }
}
