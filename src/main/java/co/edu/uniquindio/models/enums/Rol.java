package co.edu.uniquindio.models.enums;

import lombok.Getter;

@Getter
public enum Rol {

    ADMINISTRADOR("Administrador del sistema"),
    PACIENTE("Paciente de la Clínica"),
    PROFESIONAL("Profesionales del área de la salud"),
    AGENTE("Agente del Call Center");

    private final String value;
    Rol(String value){
        this.value = value;
    }
}
