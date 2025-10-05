package co.edu.uniquindio.servicios.interfaces;

import co.edu.uniquindio.dto.medico.CrearMedicoDTO;
import co.edu.uniquindio.dto.medico.EditarMedicoDTO;
import co.edu.uniquindio.dto.medico.MedicoDTO;
import co.edu.uniquindio.models.enums.Especialidad;

import java.util.List;

public interface MedicoServicio {
    void crear(CrearMedicoDTO medicoDTO) throws Exception;
    void editar(String id, EditarMedicoDTO medicoDTO) throws Exception;
    void eliminar(String id) throws Exception;
    void eliminarPorIdentificacion(String identificacion) throws Exception;
    MedicoDTO obtener(String id) throws Exception;
    MedicoDTO obtenerPorIdentificacion(String identificacion) throws Exception;

    // Métodos específicos de médico
    List<MedicoDTO> listarTodos() throws Exception;
    List<MedicoDTO> listarPorEspecialidad(Especialidad especialidad) throws Exception;
    List<MedicoDTO> listarActivos() throws Exception;
    void cambiarEstado(String id, Boolean estado) throws Exception;
}