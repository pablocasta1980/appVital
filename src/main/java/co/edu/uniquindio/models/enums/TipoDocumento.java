package co.edu.uniquindio.models.enums;

import lombok.Getter;

@Getter
public enum TipoDocumento {

    CC("Cédula de Ciudadania"),
    CE("Cédula de Extranjería"),
    PP("Permiso Especial de Permanencia"),
    RC("Registro Civil"),
    TI("Tarjeta de Identidad");

    private final String value;
    TipoDocumento(String value){
        this.value = value;
    }
}
