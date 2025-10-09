package co.edu.uniquindio.exepciones.exceptions.especialidad;

import co.edu.uniquindio.models.enums.TipoError;

public class EspecialidadNoEditadaException extends Exception{

    private final TipoError tipoError;

    public EspecialidadNoEditadaException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public EspecialidadNoEditadaException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public EspecialidadNoEditadaException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
