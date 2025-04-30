package org.iesalandalus.programacion.damas.modelo;

import java.util.Objects; // Necesario para equals y hashCode

public class Posicion {

    static final int MIN_FILA = 1;
    static final int MAX_FILA = 8;
    private static final char MIN_COLUMNA = 'a';
    private static final char MAX_COLUMNA = 'h';

    private int fila;
    private char columna;

    // Constructor (fila, columna)
    public Posicion(int fila, char columna) {
        setFila(fila); // Utiliza el setter para la validación
        setColumna(columna); // Utiliza el setter para la validación
    }

    // Constructor copia
    public Posicion(Posicion posicion) {
        if (posicion == null) {
            throw new IllegalArgumentException("No se puede copiar una posición nula.");
        }
        this.fila = posicion.fila;
        this.columna = posicion.columna;
    }

    // Getters
    public int getFila() {
        return fila;
    }

    public char getColumna() {
        return columna;
    }

    // Setters con validación
    public void setFila(int fila) {
        if (fila < MIN_FILA || fila > MAX_FILA) {
            throw new IllegalArgumentException("ERROR: La fila debe estar entre " + MIN_FILA + " y " + MAX_FILA + ".");
        }
        this.fila = fila;
    }

    public void setColumna(char columna) {
        if (columna < MIN_COLUMNA || columna > MAX_COLUMNA) {
            throw new IllegalArgumentException("ERROR: La columna debe estar entre '" + MIN_COLUMNA + "' y '" + MAX_COLUMNA + "'.");
        }
        this.columna = columna;
    }

    // Métodos equals y hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Posicion posicion = (Posicion) obj;
        return fila == posicion.fila && columna == posicion.columna;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fila, columna);
    }

    // Método toString
    @Override
    public String toString() {
        return String.format("fila=%d, columna=%c", fila, columna);
    }
}
