package co.edu.uniquindio.services.implementations;

import co.edu.uniquindio.config.PlantillasEmailConfig;
import co.edu.uniquindio.controllers.exceptions.cita.*;
import co.edu.uniquindio.dto.cita.*;
import co.edu.uniquindio.controllers.exceptions.cuenta.CuentaNoEncontradaException;
import co.edu.uniquindio.controllers.exceptions.cuenta.ProfesionalesNoEncontradosException;
import co.edu.uniquindio.controllers.exceptions.especialidad.EspecialidadNoEncontradaException;
import co.edu.uniquindio.controllers.exceptions.sede.SedeNoEncontradaException;
import co.edu.uniquindio.dto.email.EmailDTO;
import co.edu.uniquindio.models.documents.Cita;
import co.edu.uniquindio.models.documents.Cuenta;
import co.edu.uniquindio.models.documents.Especialidad;
import co.edu.uniquindio.models.documents.Sede;
import co.edu.uniquindio.models.enums.EstadoCita;
import co.edu.uniquindio.models.enums.EstadoRegistro;
import co.edu.uniquindio.models.vo.Paciente;
import co.edu.uniquindio.models.vo.Profesional;
import co.edu.uniquindio.models.vo.Usuario;
import co.edu.uniquindio.repository.ICitaRepository;
import co.edu.uniquindio.repository.ICuentaRepository;
import co.edu.uniquindio.repository.IEspecialidadRepository;
import co.edu.uniquindio.repository.ISedeRepository;
import co.edu.uniquindio.services.interfaces.ICitaService;
import co.edu.uniquindio.services.interfaces.IEmailService;
import co.edu.uniquindio.utils.GenerarCodigo;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements ICitaService {

    private final ICuentaRepository cuentaRepository;
    private final ICitaRepository citaRepository;
    private final ISedeRepository sedeRepository;
    private final IEmailService emailService;
    private final IEspecialidadRepository especialidadRepository;
    private final GenerarCodigo generarCodigo;
    @Override
    public String crearCitaMedica(CrearCitaDTO crearCitaDTO) throws CitaNoCreadaException {

        try {
            String codigoCita = generarCodigo.generarCodigoAleatorioCita();
            ObjectId objectMedico = new ObjectId( crearCitaDTO.idMedico() );
            ObjectId objectPaciente = new ObjectId( crearCitaDTO.idPaciente() );
            ObjectId objectSede = new ObjectId( crearCitaDTO.idSede() );
            ObjectId objectUsuario = new ObjectId( crearCitaDTO.usuarioCreacion() );

            Cita crearCita = new Cita();
            crearCita.setCodigo( codigoCita );
            crearCita.setIdMedico( objectMedico );
            crearCita.setIdPaciente( objectPaciente );
            crearCita.setIdSede( objectSede );
            crearCita.setConfirmada( false );
            crearCita.setFechaCita( crearCitaDTO.fechaCita() );
            crearCita.setEspecialidad( crearCitaDTO.especialidad() );
            crearCita.setDuracion( crearCitaDTO.duracion() );
            crearCita.setConsultorio( crearCitaDTO.consultorio() );
            crearCita.setComentarios( crearCitaDTO.comentarios() );
            crearCita.setEstado( EstadoCita.PROGRAMADA );
            crearCita.setEstadoRegistro( EstadoRegistro.ACTIVO.getValue() );
            crearCita.setUsuarioCreacion( objectUsuario );
            crearCita.setFechaCreacion( crearCitaDTO.fechaCreacion() );

            Optional<Sede> sede = sedeRepository.findById( crearCitaDTO.idSede() );
            Optional<Cuenta> cuentaPaciente = cuentaRepository.findById( crearCitaDTO.idPaciente() );
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String fechaCita = crearCitaDTO.fechaCita().format(dateFormatter);


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", new Locale("es", "ES"));
            String horaCita = crearCitaDTO.fechaCita().format(formatter);

            String body = PlantillasEmailConfig.bodyNotificacionCita.replace("[especialidad]",
                    crearCitaDTO.especialidad()).replace("[fecha_cita]", fechaCita
            ).replace("[hora_cita]", horaCita).replace("[sede]",
                    sede.get().getNombre()).replace("[consultorio]", crearCitaDTO.consultorio() );

            emailService.enviarCorreo( new EmailDTO(cuentaPaciente.get().getEmail(), "Cita Asignada", body) );

            citaRepository.save( crearCita );

            return "Cita creada exitosamente.";

        } catch (Exception e) {
            throw new CitaNoCreadaException("La cita no fue creada. " + e.getMessage());
        }
    }

    @Override
    public String editarCitaMedica(EditarCitaDTO editarCitaDTO) throws CitaNoEditadaException {

        try {

            return  "";
        } catch (Exception e){
            throw new CitaNoEditadaException("La cita no fue creada. " + e.getMessage());
        }
    }

    @Override
    public String eliminarCitaMedica(EliminarCitaDTO eliminarCitaDTO) throws CitaNoEliminadaException {
        return null;
    }

    @Override
    public InformacionCitaDTO obtenerInformacionCitaDTO(String idCita) throws CitaNoEncontradaException, PacienteNoAfiliadoException, SedeNoEncontradaException {

        try {
            Cita cita = encontrarCitaPorId( idCita );
            Paciente paciente = encontrarPacientePorId( cita.getIdPaciente().toHexString() );
            Sede sede = encontrarSedePorId( cita.getIdSede().toHexString() );
            Usuario usuario = encontrarCuentaPorId( cita.getUsuarioCreacion().toHexString() );

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String fechaCita = cita.getFechaCita().format(dateFormatter);
            String fechaCreacion = cita.getFechaCita().format(dateFormatter);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String horaCita = cita.getFechaCita().format(formatter);
            String horaCreacion = cita.getFechaCita().format(formatter);

            return new InformacionCitaDTO(
                    cita.getId(),
                    cita.getCodigo(),
                    paciente.getNombres() + " " + paciente.getApellidos(),
                    paciente.getTipoDocumento(),
                    paciente.getNroDocumento(),
                    paciente.getCelular(),
                    sede.getNombre(),
                    cita.isConfirmada(),
                    fechaCita,
                    horaCita,
                    cita.getEspecialidad(),
                    cita.getDuracion(),
                    cita.getConsultorio(),
                    cita.getComentarios(),
                    cita.getEstado().toString(),
                    cita.getEstadoRegistro(),
                    usuario.getNombres() + " " + usuario.getApellidos(),
                    fechaCreacion,
                    horaCreacion
            );

        }catch (CitaNoEncontradaException e) {
            throw new CitaNoEncontradaException("Cita con ID " + idCita + " no fue encontrada.");
        } catch (PacienteNoAfiliadoException e) {
            throw new PacienteNoAfiliadoException("El paciente asociado con la cita no está afiliado.");
        } catch (SedeNoEncontradaException e) {
            throw new SedeNoEncontradaException("La sede asociada con la cita no fue encontrada.");
        } catch (Exception e) {
            throw new RuntimeException("Ocurrió un error inesperado al obtener la información de la cita.", e);
        }
    }

    @Override
    public List<ItemCitaDTO> listarCitas() throws CitaNoEncontradaException {
        return null;
    }

    @Override
    public List<ItemCitaDTO> listarCitasPorMedico(String idMedico) throws CitaNoEncontradaException {
        return null;
    }

    @Override
    public List<InformacionCitaDTO> citasPorMedico(String idMedico) throws CitaNoEncontradaException {

        ObjectId objectIdMedico = new ObjectId( idMedico );
        try {
            Optional<List<Cita>> citas = citaRepository.findCitasByIdMedico( objectIdMedico );

            if( citas.isEmpty() || citas.get().isEmpty()){
                throw new CitaNoEncontradaException("Error al buscar las citas del profesional.");
            }

            List<InformacionCitaDTO> citasInfo = new ArrayList<>();
            for(Cita cita : citas.get() ){
                InformacionCitaDTO info = obtenerInformacionCitaDTO( cita.getId() );

                citasInfo.add( info );
            }

            return citasInfo;

        }catch(Exception e){
            throw new CitaNoEncontradaException("Error al carga citas por médico." +e.getMessage());
        }
    }

    @Override
    public List<ItemCitaDTO> listarCitasPorEsepcialidad(String idEspecialidad) throws CitaNoEncontradaException {
        return null;
    }

    @Override
    public List<ItemCitaDTO> listarCitasPorFechaCita(LocalDateTime fechaCita) throws CitaNoEncontradaException {
        return null;
    }

    public Paciente encontrarPacientePorId(String id) throws PacienteNoAfiliadoException {
        try {
            Optional<Cuenta> cuenta = cuentaRepository.findById( id );

            if( cuenta.isEmpty() ){
                throw new CuentaNoEncontradaException("La Cuenta no se encuentra registrado en la Clínica.");
            }

            if( !(cuenta.get().getUsuario() instanceof Paciente) ){
                throw new PacienteNoAfiliadoException("El id no esta asociado a un paciente.");
            }

            return (Paciente) cuenta.get().getUsuario();

        } catch (Exception e){
            throw new PacienteNoAfiliadoException("El paciente no fue encontrado " + e.getMessage());
        }
    }

    public Cita encontrarCitaPorId(String id) throws CitaNoEncontradaException {
        try {
            Optional<Cita> cita = citaRepository.findById( id );

            if( cita.isEmpty() ){
                throw new CuentaNoEncontradaException("La Cita no se encuentra registrada en la Clínica.");
            }

            return cita.get();

        } catch (Exception e){
            throw new CitaNoEncontradaException("La Cita no fue encontrada " + e.getMessage());
        }
    }

    public Profesional encontrarProfesionalPorId(String id) throws ProfesionalesNoEncontradosException {
        try {
            Optional<Cuenta> cuenta = cuentaRepository.findById( id );

            if( cuenta.isEmpty() ){
                throw new CuentaNoEncontradaException("La Cuenta no se encuentra registrado en la Clínica.");
            }

            if( !(cuenta.get().getUsuario() instanceof Profesional) ){
                throw new ProfesionalesNoEncontradosException("El id no esta asociado a un profesional.");
            }

            return (Profesional) cuenta.get().getUsuario();

        } catch (Exception e ){
            throw new ProfesionalesNoEncontradosException("Error al traer el profesional." + e.getMessage());
        }
    }

    public Sede encontrarSedePorId(String id) throws SedeNoEncontradaException {
        try {
            Optional<Sede> sede = sedeRepository.findById( id );

            if( sede.isEmpty() ){
                throw new SedeNoEncontradaException("La sede no existe.");
            }

            return sede.get();
        } catch (Exception e ){
            throw new SedeNoEncontradaException("La sede indicada no existe." + e.getMessage());
        }
    }

    public Usuario encontrarCuentaPorId(String id) throws CuentaNoEncontradaException {
        try {
            Optional<Cuenta> cuenta = cuentaRepository.findById( id );

            if( cuenta.isEmpty() ){
                throw new CuentaNoEncontradaException("La Cuenta no se encuentra registrado en la Clínica.");
            }

            return cuenta.get().getUsuario();

        } catch (Exception e){
            throw new CuentaNoEncontradaException("La cuenta que creo el registro no fue encontrado " + e.getMessage());
        }
    }

    private Especialidad encontrarEspecialidadPorId( String id ) throws EspecialidadNoEncontradaException{
        try {
            Optional<Especialidad> especialidad = especialidadRepository.findById( id );

            if( especialidad.isEmpty() ){
                throw new EspecialidadNoEncontradaException("Especialidad de la cita no encontrada.");
            }

            return especialidad.get();
        } catch (Exception e){
            throw  new EspecialidadNoEncontradaException("Error al tratar de encontrar la especialidad asociada a la cita. " + e.getMessage());
        }
    }
}
