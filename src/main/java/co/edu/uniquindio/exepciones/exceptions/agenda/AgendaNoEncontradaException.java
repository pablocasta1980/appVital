package co.edu.uniquindio.exepciones.exceptions.agenda;

import co.edu.uniquindio.models.enums.TipoError;

public class AgendaNoEncontradaException extends Exception{

    private final TipoError tipoError;

    public AgendaNoEncontradaException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public AgendaNoEncontradaException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public AgendaNoEncontradaException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
