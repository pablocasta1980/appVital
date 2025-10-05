package co.edu.uniquindio.models.enums;

import lombok.Getter;

@Getter
public enum Ciudad {
    ARMENIA("Armenia"),
    CALARCA("Calárca"),
    MANIZALES("Manizales"),
    PEREIRA("Pereira"),
    DOSQUEBRADAS("Dosquebradas");

    private final String value;
    Ciudad(String value){
        this.value = value;
    }
}
