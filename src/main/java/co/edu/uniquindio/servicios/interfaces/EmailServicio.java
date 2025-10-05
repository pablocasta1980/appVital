package co.edu.uniquindio.servicios.interfaces;

import co.edu.uniquindio.dto.EmailDTO;

public interface EmailServicio {
    void enviarCorreo(EmailDTO emailDTO) throws Exception;
}