package co.edu.uniquindio.controllers.exceptions.cita;

import co.edu.uniquindio.models.enums.TipoError;

public class PacienteNoAfiliadoException extends Exception{

    private final TipoError tipoError;

    public PacienteNoAfiliadoException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public PacienteNoAfiliadoException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public PacienteNoAfiliadoException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
