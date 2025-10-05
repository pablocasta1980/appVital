package co.edu.uniquindio.services.interfaces;

import co.edu.uniquindio.controllers.exceptions.cita.*;
import co.edu.uniquindio.dto.cita.*;
import co.edu.uniquindio.controllers.exceptions.cuenta.ProfesionalesNoEncontradosException;
import co.edu.uniquindio.controllers.exceptions.sede.SedeNoEncontradaException;

import java.time.LocalDateTime;
import java.util.List;

public interface ICitaService {

    String crearCitaMedica(CrearCitaDTO crearCitaDTO) throws CitaNoCreadaException, PacienteNoAfiliadoException;
    String editarCitaMedica(EditarCitaDTO editarCitaDTO) throws CitaNoEditadaException;
    String eliminarCitaMedica(EliminarCitaDTO eliminarCitaDTO) throws CitaNoEliminadaException;
    InformacionCitaDTO obtenerInformacionCitaDTO(String idCita) throws CitaNoEncontradaException, PacienteNoAfiliadoException, SedeNoEncontradaException, ProfesionalesNoEncontradosException;
    List<ItemCitaDTO> listarCitas() throws CitaNoEncontradaException;
    List<ItemCitaDTO> listarCitasPorMedico(String idMedico) throws CitaNoEncontradaException;
    List<InformacionCitaDTO> citasPorMedico(String idMedico) throws CitaNoEncontradaException;
    List<ItemCitaDTO> listarCitasPorEsepcialidad(String idEspecialidad) throws CitaNoEncontradaException;
    List<ItemCitaDTO> listarCitasPorFechaCita(LocalDateTime fechaCita) throws CitaNoEncontradaException;
}