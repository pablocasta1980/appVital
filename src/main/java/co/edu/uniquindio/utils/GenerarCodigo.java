package co.edu.uniquindio.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class GenerarCodigo {

    @Value("${longitud.codigo}")
    private int longitudCodigo;

    @Value("${longitud.codigo.cita}")
    private int longitudCodigoCita;

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generarCodigoAleatorio(){
        return generarCodigo(longitudCodigo);
    }

    public String generarCodigoAleatorioCita(){
        return generarCodigo(longitudCodigoCita);
    }

    private String generarCodigo(int longitud){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            int character = RANDOM.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}