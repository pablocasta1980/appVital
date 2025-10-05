package co.edu.uniquindio.models.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "citas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cita {

    @Id
    private String id;

    private String pacienteId;
    private String doctorId;
    private LocalDateTime fechaHora;
    private String motivo;
}