package co.edu.uniquindio.controllers.exceptions.sede;

import co.edu.uniquindio.models.enums.TipoError;

public class SedeNoEncontradaException extends Exception{

    private final TipoError tipoError;

    public SedeNoEncontradaException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public SedeNoEncontradaException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public SedeNoEncontradaException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
