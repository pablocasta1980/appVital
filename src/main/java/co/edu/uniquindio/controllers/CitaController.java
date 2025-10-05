package co.edu.uniquindio.controllers;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
import co.edu.uniquindio.models.documents.Cita;
import co.edu.uniquindio.servicios.interfaces.ICitaService;
=======
=======
>>>>>>> Stashed changes
import co.edu.uniquindio.dto.cita.InformacionCitaDTO;
import co.edu.uniquindio.dto.cuenta.MensajeDTO;
import co.edu.uniquindio.controllers.exceptions.cita.CitaNoEncontradaException;
import co.edu.uniquindio.controllers.exceptions.cita.PacienteNoAfiliadoException;
import co.edu.uniquindio.controllers.exceptions.cuenta.ProfesionalesNoEncontradosException;
import co.edu.uniquindio.controllers.exceptions.sede.SedeNoEncontradaException;
import co.edu.uniquindio.services.interfaces.ICitaService;
>>>>>>> Stashed changes
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/citas")
public class CitaController {

    private final ICitaService citaService;

    @GetMapping("/obtener-cita/{idCita}")
    public ResponseEntity<MensajeDTO<InformacionCitaDTO>> obtenerCitas(@PathVariable String idCita) throws SedeNoEncontradaException, PacienteNoAfiliadoException, ProfesionalesNoEncontradosException, CitaNoEncontradaException {
        return ResponseEntity.ok().body( new MensajeDTO<>( false, citaService.obtenerInformacionCitaDTO( idCita )) );
    }

<<<<<<< Updated upstream
<<<<<<< Updated upstream
    @GetMapping
    public ResponseEntity<List<Cita>> listarCitas() {
        return ResponseEntity.ok(citaService.listarCitas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerCita(@PathVariable String id) {
        return citaService.obtenerCitaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable String id) {
        citaService.eliminarCita(id);
        return ResponseEntity.noContent().build();
    }
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}