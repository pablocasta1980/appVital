package co.edu.uniquindio.exepciones.exceptions.cuenta;

import co.edu.uniquindio.models.enums.TipoError;

public class SesionNoIniciadaException extends Exception{

    private final TipoError tipoError;

    public SesionNoIniciadaException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public SesionNoIniciadaException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public SesionNoIniciadaException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
