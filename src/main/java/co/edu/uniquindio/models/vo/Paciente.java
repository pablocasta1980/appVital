package co.edu.uniquindio.models.vo;

import co.edu.uniquindio.models.enums.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Paciente extends Usuario{

    private Nacionalidad nacionalidad;
    private LocalDate fechaNacimiento;
    private String departamento;
    private String ciudad;
    private String celular;
    private Regimen regimen;
    private String planComplementario;

    public Paciente(
            String tipoDocumento,
            String nroDocumento,
            String direccion,
            String nombres,
            String apellidos,
            Nacionalidad nacionalidad,
            LocalDate  fechaNacimiento,
            String departamento,
            String ciudad,
            String celular,
            Regimen regimen,
            String planComplementario) {
        super(tipoDocumento, nroDocumento, direccion, nombres, apellidos);
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.departamento = departamento;
        this.ciudad = ciudad;
        this.celular = celular;
        this.regimen = regimen;
        this.planComplementario = planComplementario;
    }
}
