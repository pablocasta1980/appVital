// src/main/java/co/edu/uniquindio/service/interfaces/AuthService.java
package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.domain.dto.request.LoginRequest;
import co.edu.uniquindio.domain.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
}