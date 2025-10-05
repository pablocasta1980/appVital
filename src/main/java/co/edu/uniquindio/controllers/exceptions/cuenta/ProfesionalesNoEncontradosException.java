package co.edu.uniquindio.controllers.exceptions.cuenta;

import co.edu.uniquindio.models.enums.TipoError;

public class ProfesionalesNoEncontradosException extends Exception{

    private final TipoError tipoError;

    public ProfesionalesNoEncontradosException(String mensaje){
        super(mensaje);
        this.tipoError = TipoError.UNKNOWN_ERROR;
    }

    public ProfesionalesNoEncontradosException(String mensaje, TipoError tipoError) {
        super(mensaje);
        this.tipoError = tipoError;
    }

    public ProfesionalesNoEncontradosException(String mensaje, TipoError tipoError, Throwable causa) {
        super(mensaje, causa);
        this.tipoError = tipoError;
    }

    public TipoError obtenerTipoError(){
        return tipoError;
    }
}
