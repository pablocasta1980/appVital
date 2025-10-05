package co.edu.uniquindio.models.enums;

import lombok.Getter;

@Getter
public enum Linea {

    UNO("01"),
    DOS("02"),
    TRES("03"),
    CUATRO("04"),
    CINCO("05");

    private final String value;
    Linea(String value){
        this.value = value;
    }
}
