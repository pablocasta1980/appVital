package co.edu.uniquindio.services.interfaces;

import co.edu.uniquindio.dto.email.EmailDTO;

public interface IEmailService {

    void enviarCorreo(EmailDTO emailDTO) throws Exception;
}
