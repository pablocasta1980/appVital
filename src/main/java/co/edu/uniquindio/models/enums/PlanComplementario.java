package co.edu.uniquindio.models.enums;

import lombok.Getter;

@Getter
public enum PlanComplementario {
    PREFERENCIAL("Plan Preferencial"),
    PREMIUM("Plan Premium"),
    SIN_PLAN("Paciente sin Plan Complementario");

    private final String value;
    PlanComplementario(String value){
        this.value = value;
    }
}
