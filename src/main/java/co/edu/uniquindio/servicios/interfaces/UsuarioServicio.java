package co.edu.uniquindio.servicios.interfaces;

import co.edu.uniquindio.dto.CrearUsuarioDTO;
import co.edu.uniquindio.dto.EditarUsuarioDTO;
import co.edu.uniquindio.dto.UsuarioDTO;
import jakarta.validation.Valid;

public interface UsuarioServicio {


    void crear(CrearUsuarioDTO cuenta) throws Exception;

    void editar(String id,@Valid EditarUsuarioDTO cuenta) throws Exception;

    void eliminar(String id) throws Exception;

    void eliminarPorIdentificacion(String identificacion) throws Exception; // ← NUEVO

    UsuarioDTO obtener(String id) throws Exception;

    UsuarioDTO obtenerPorIdentificacion(String identificacion) throws Exception;
}





