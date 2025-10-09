package co.edu.uniquindio.exepciones.exceptions.cuenta;

import co.edu.uniquindio.models.enums.TipoError;

public class PasswordNoEditadaException extends Exception{

    private final TipoError tipoError;

    public PasswordNoEditadaException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public PasswordNoEditadaException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public PasswordNoEditadaException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
