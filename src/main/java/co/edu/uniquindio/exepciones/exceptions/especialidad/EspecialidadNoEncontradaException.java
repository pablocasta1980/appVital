package co.edu.uniquindio.exepciones.exceptions.especialidad;

import co.edu.uniquindio.models.enums.TipoError;

public class EspecialidadNoEncontradaException extends Exception{

    private final TipoError tipoError;

    public EspecialidadNoEncontradaException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public EspecialidadNoEncontradaException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public EspecialidadNoEncontradaException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
