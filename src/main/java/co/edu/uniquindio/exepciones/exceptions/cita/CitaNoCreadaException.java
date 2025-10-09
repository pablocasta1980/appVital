package co.edu.uniquindio.exepciones.exceptions.cita;

import co.edu.uniquindio.models.enums.TipoError;

public class CitaNoCreadaException extends Exception{

    private final TipoError tipoError;

    public CitaNoCreadaException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public CitaNoCreadaException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public CitaNoCreadaException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
