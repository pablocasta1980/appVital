package co.edu.uniquindio.services.implementations;

import co.edu.uniquindio.dto.email.EmailDTO;
import co.edu.uniquindio.services.interfaces.IEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;


@Service
public class EmailServiceImpl implements IEmailService {

    @Value("${correo.puerto}")
    private Integer puerto;

    @Value("${correo.smtp.username}")
    private String smtpUsername;

    @Value("${correo.smtp.password}")
    private String smtpPassword;

    @Value("${correo.smtp.host}")
    private String smtpHost;
    @Override
    @Async
    public void enviarCorreo(EmailDTO emailDTO) throws Exception {

        Email email = EmailBuilder.startingBlank()
                .from(smtpUsername)
                .to(emailDTO.destinatario())
                .withSubject(emailDTO.asunto())
                .withHTMLText(emailDTO.cuerpo())
                .buildEmail();


        try (Mailer mailer = MailerBuilder
                .withSMTPServer(smtpHost, puerto, smtpUsername, smtpPassword)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {


            mailer.sendMail(email);
        }

    }

}
