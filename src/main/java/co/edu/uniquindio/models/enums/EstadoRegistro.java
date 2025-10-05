package co.edu.uniquindio.models.enums;

import lombok.Getter;

@Getter
public enum EstadoRegistro {

    ACTIVO("Activo"),
    INACTIVO("Inactivo");
    
    private final String value;
    EstadoRegistro(String value){
        this.value = value;
    }
}
