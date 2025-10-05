package co.edu.uniquindio.servicios.interfaces;

import co.edu.uniquindio.dto.auth.LoginDTO;
import co.edu.uniquindio.dto.auth.TokenDTO;

public interface AuthServicio {
    TokenDTO login(LoginDTO loginDTO) throws Exception;
}