package org.iesalandalus.programacion.damas.modelo; // Según el diagrama, los enums están en .modelo

public enum Direccion {
    NORESTE("Noreste"),
    SURESTE("Sureste"),
    SUROESTE("Suroeste"),
    NOROESTE("Noroeste");

    private String cadenaAMostrar;

    // Constructor con visibilidad adecuada (privado para enums)
    private Direccion(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    // Método toString
    @Override
    public String toString() {
        return cadenaAMostrar;
    }
}