package co.edu.uniquindio.servicios.interfaces;

import co.edu.uniquindio.dto.paciente.CrearPacienteDTO;
import co.edu.uniquindio.dto.paciente.EditarPacienteDTO;
import co.edu.uniquindio.dto.paciente.PacienteDTO;

public interface PacienteServicio {
    void crear(CrearPacienteDTO pacienteDTO) throws Exception;
    void editar(String id, EditarPacienteDTO pacienteDTO) throws Exception;
    void eliminar(String id) throws Exception;
    void eliminarPorIdentificacion(String identificacion) throws Exception;
    PacienteDTO obtener(String id) throws Exception;
    PacienteDTO obtenerPorIdentificacion(String identificacion) throws Exception;
}