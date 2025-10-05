package co.edu.uniquindio.models.enums;

import lombok.Getter;

@Getter
public enum RolUsuario {

    ADMINISTRADOR("Administrador del sistema"),
    PACIENTE("Paciente de la Clínica"),
    MEDICO ("Profesionales del área de la salud"),
    AGENTE("Agente del Call Center");

    private final String value;
    RolUsuario(String value){
        this.value = value;
    }
}
