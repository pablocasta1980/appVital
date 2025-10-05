package co.edu.uniquindio.models.enums;

import lombok.Getter;

@Getter
public enum DuracionAgenda {

    VEINTE("20 Minutos"),
    TREINTA("30 Minutos"),
    CUARENTA("40 Minutos");

    private final String value;
    DuracionAgenda(String value){
        this.value = value;
    }
}
