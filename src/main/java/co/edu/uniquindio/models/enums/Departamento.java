package co.edu.uniquindio.models.enums;

import lombok.Getter;

@Getter
public enum Departamento {
    CALDAS("Caldas") {
        @Override
        public Ciudad[] getCiudades() {
            return new Ciudad[] { Ciudad.PEREIRA, Ciudad.DOSQUEBRADAS };
        }
    },
    RISARALDA("Risaralda") {
        @Override
        public Ciudad[] getCiudades() {
            return new Ciudad[] { Ciudad.PEREIRA, Ciudad.DOSQUEBRADAS };
        }
    },
    QUINDIO("Quindío") {  // El nombre del enum no lleva tilde, pero el valor sí
        @Override
        public Ciudad[] getCiudades() {
            return new Ciudad[] { Ciudad.ARMENIA, Ciudad.CALARCA };
        }
    };

    private final String nombre;

    Departamento(String nombre) {
        this.nombre = nombre;
    }

    public abstract Ciudad[] getCiudades();

    // Método para obtener el enum por su atributo `nombre`
    public static Departamento fromNombre(String nombre) {
        for (Departamento d : Departamento.values()) {
            if (d.getNombre().equalsIgnoreCase(nombre)) {
                return d;
            }
        }
        throw new IllegalArgumentException("No enum constant with nombre: " + nombre);
    }
}
