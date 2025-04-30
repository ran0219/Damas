package org.iesalandalus.programacion.damas.modelo; // Según el diagrama, los enums están en .modelo

public enum Color {
    BLANCO("Blanco"),
    NEGRO("Negro");

    private String cadenaAMostrar;

    // Constructor con visibilidad adecuada (privado para enums)
    private Color(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    // Método toString
    @Override
    public String toString() {
        return cadenaAMostrar;
    }
}