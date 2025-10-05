package co.edu.uniquindio.servicios.implementations;

import co.edu.uniquindio.models.documents.Cita;
import co.edu.uniquindio.repository.ICitaRepository;
import co.edu.uniquindio.servicios.interfaces.ICitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements ICitaService {

    private final ICitaRepository citaRepository; // ✅ repositorio, no el servicio

    @Override
    public Cita crearCita(Cita cita) {
        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    @Override
    public Optional<Cita> obtenerCitaPorId(String id) {
        return citaRepository.findById(id);
    }

    @Override
    public void eliminarCita(String id) {
        citaRepository.deleteById(id);
    }
}
