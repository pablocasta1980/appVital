package co.edu.uniquindio.models.enums;

import lombok.Getter;

@Getter
public enum NivelAcceso {
    BASIC("Acceso básico"),
    MEDIUM("Acceso medio"),
    HIGH("Acceso completo");

    private final String value;
    NivelAcceso(String value){
        this.value = value;
    }
}
