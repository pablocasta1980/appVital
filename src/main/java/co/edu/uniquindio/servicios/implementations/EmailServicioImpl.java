package co.edu.uniquindio.servicios.implementations;

import co.edu.uniquindio.dto.EmailDTO;
import co.edu.uniquindio.servicios.interfaces.EmailServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServicioImpl implements EmailServicio {

    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void enviarCorreo(EmailDTO emailDTO) throws Exception {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("coordinadorelectricoberhlan@gmail.com");
        mailMessage.setTo(emailDTO.destinatario());
        mailMessage.setSubject(emailDTO.asunto());
        mailMessage.setText(emailDTO.cuerpo());

        javaMailSender.send(mailMessage);
    }
}