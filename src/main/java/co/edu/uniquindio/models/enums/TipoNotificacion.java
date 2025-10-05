package co.edu.uniquindio.models.enums;

import lombok.Getter;

@Getter
public enum TipoNotificacion {
    CUENTA_CREADA("Creación de una nueva cuenta"),
    CUENTA_CERRADA("Eliminación de una cuenta"),
    CAMBIO_PASSWORD("Cambio de contraseña en la cuenta"),
    AGENDADO("Agendamiento de una nueva cita"),
    REPROGRAMADO("Reprogramación de cita"),
    CANCELACION("Cancelación de una cita");

    private final String value;
    TipoNotificacion(String value){
        this.value = value;
    }
}
