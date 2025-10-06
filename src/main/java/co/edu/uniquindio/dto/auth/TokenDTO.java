package co.edu.uniquindio.dto.auth;

public record TokenDTO(
        String token,
        String id,
        String nombre,
        String email,
        String rol

) {
    // ✅ Constructor viejo para mantener compatibilidad
    public TokenDTO(String token) {
        this(token, null, null, null, null);
    }

    // ✅ Constructor completo
    public TokenDTO(String token, String id, String nombre, String email, String rol) {
        this.token = token;
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;

    }
}