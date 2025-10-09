package co.edu.uniquindio.exepciones.exceptions.especialidad;

import co.edu.uniquindio.models.enums.TipoError;

public class EspecialidadNoCreadaException extends Exception{

    private final TipoError tipoError;

    public EspecialidadNoCreadaException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public EspecialidadNoCreadaException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public EspecialidadNoCreadaException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
