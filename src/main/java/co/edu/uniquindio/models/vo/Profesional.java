package co.edu.uniquindio.models.vo;

import lombok.*;
import org.bson.types.ObjectId;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Profesional extends Usuario{
    private List<ObjectId> especialidad;

    public Profesional(
            String tipoDocumento,
            String nroDocumento,
            String direccion,
            String nombres,
            String apellidos,
            List<ObjectId> especialidad) {
        super(tipoDocumento, nroDocumento, direccion, nombres, apellidos);
        this.especialidad = especialidad;
    }
}
