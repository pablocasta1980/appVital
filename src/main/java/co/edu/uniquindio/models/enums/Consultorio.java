package co.edu.uniquindio.models.enums;

import lombok.Getter;

@Getter
public enum Consultorio {

    UNO("Consultorio 001"),
    DOS("Consultorio 002"),
    TRES("Consultorio 003"),
    CUATRO("Consultorio 004"),
    CINCO("Consultorio 005");

    private final String value;
    Consultorio(String value){
        this.value = value;
    }
}
