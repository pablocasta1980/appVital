package co.edu.uniquindio.servicios.interfaces;

import co.edu.uniquindio.models.documents.Cita;
import java.util.List;
import java.util.Optional;

public interface ICitaService {
    Cita crearCita(Cita cita);
    List<Cita> listarCitas();
    Optional<Cita> obtenerCitaPorId(String id);
    void eliminarCita(String id);
}