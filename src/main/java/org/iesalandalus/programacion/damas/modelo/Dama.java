package org.iesalandalus.programacion.damas.modelo;

import java.util.Objects;
import java.util.Random;
import javax.naming.OperationNotSupportedException;

public class Dama {

    private static final int MIN_FILA_BLANCAS = 1;
    private static final int MAX_FILA_BLANCAS = 3;
    private static final int MIN_FILA_NEGRAS = 6;
    private static final int MAX_FILA_NEGRAS = 8;
    private static final char MIN_COLUMNA = 'a';
    private static final char MAX_COLUMNA = 'h';

    private Color color;
    private Posicion posicion;
    private boolean esDamaEspecial;

    // Constructor por defecto
    public Dama() {
        setColor(Color.BLANCO); // Dama blanca por defecto
        this.posicion = crearPosicionInicial(Color.BLANCO); // Posición inicial aleatoria para blanca
        this.esDamaEspecial = false;
    }

    // Constructor (color)
    public Dama(Color color) {
        setColor(color);
        this.posicion = crearPosicionInicial(color); // Posición inicial aleatoria según el color
        this.esDamaEspecial = false;
    }

    // Getters
    public Color getColor() {
        return color;
    }

    public Posicion getPosicion() {
        // Devolvemos una copia para evitar que se modifique desde fuera
        return new Posicion(posicion);
    }

    public boolean esDamaEspecial() {
        return esDamaEspecial;
    }

    // Setters con validación
    public void setColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("ERROR: El color no puede ser nulo.");
        }
        this.color = color;
    }

    public void setPosicion(Posicion posicion) {
        // No se pide validación extra en el setter, pero aseguramos que no sea nula.
        // La validación de límites se hace en la clase Posicion.
        if (posicion == null) {
            throw new IllegalArgumentException("ERROR: La posición no puede ser nula.");
        }
        this.posicion = new Posicion(posicion); // Asignamos una copia para evitar referencias directas
    }

    private void setEsDamaEspecial(boolean esDamaEspecial) {
        this.esDamaEspecial = esDamaEspecial;
    }

    // Método auxiliar para crear posición inicial aleatoria
    private Posicion crearPosicionInicial(Color color) {
        Random random = new Random();
        int filaAleatoria;
        char columnaAleatoria;
        Posicion nuevaPosicion;

        do {
            if (color == Color.BLANCO) {
                filaAleatoria = random.nextInt(MAX_FILA_BLANCAS - MIN_FILA_BLANCAS + 1) + MIN_FILA_BLANCAS;
            } else { // Color.NEGRO
                filaAleatoria = random.nextInt(MAX_FILA_NEGRAS - MIN_FILA_NEGRAS + 1) + MIN_FILA_NEGRAS;
            }
            columnaAleatoria = (char) (random.nextInt(MAX_COLUMNA - MIN_COLUMNA + 1) + MIN_COLUMNA);

            try {
                nuevaPosicion = new Posicion(filaAleatoria, columnaAleatoria);
            } catch (IllegalArgumentException e) {
                nuevaPosicion = null; // En caso de que se genere una posición inválida por algún motivo (raro, pero seguridad)
            }
            // Una casilla es "negra" si la suma de su fila y su columna (convertida a int) es par.
            // Ojo: 'a' es 97, 'b' es 98...
            // Una casilla es negra si (fila + columna.valorNumerico) % 2 == 0 si (1, 'a') es negra.
            // En damas, las fichas se colocan en casillas negras.
            // Para el tablero estándar (1,a) es blanca. (1,b) es negra.
            // Una casilla (fila, columna) es negra si (fila + (columna - 'a')) % 2 != 0.
            // Por ejemplo, (1, 'b'): 1 + (98-97) = 2 -> % 2 == 0 (blanca)
            // (1, 'a'): 1 + (97-97) = 1 -> % 2 != 0 (negra)
            // No, si (1,a) es negra (como en la imagen):
            // (1, 'a') -> (1 + 0) = 1 -> impar.
            // (1, 'b') -> (1 + 1) = 2 -> par.
            // (2, 'a') -> (2 + 0) = 2 -> par.
            // (2, 'b') -> (2 + 1) = 3 -> impar.
            // Las casillas negras son aquellas donde fila y columna tienen la misma paridad (ambos par o ambos impar) si 'a' es 0, 'b' es 1, etc.
            // Si (1,'a') es NEGRA, entonces (fila + (columna - 'a')) % 2 == 0 (para casillas negras en la imagen).
            // (1,a) -> 1 + 0 = 1 (impar). Fila par + columna par = negra; Fila impar + columna impar = negra.
            // (fila % 2 == 0 && (columna - 'a') % 2 == 0) || (fila % 2 != 0 && (columna - 'a') % 2 != 0)
            // Simplificado: (fila % 2) == ((columna - 'a') % 2)
            // La imagen muestra (1,'a') como negra, (1,'b') como blanca.
            // Por lo tanto, (fila + (columna - 'a' + 1)) % 2 == 0 (fila + columna_index_base_1) % 2 == 0 para casillas negras
            // (1,a) -> 1 + 1 = 2 (par)
            // (1,b) -> 1 + 2 = 3 (impar)
            // (2,a) -> 2 + 1 = 3 (impar)
            // (2,b) -> 2 + 2 = 4 (par)
            // Si la suma de fila y columna_index_base_1 es par, la casilla es negra.
            // columna_index_base_1 = (columna - 'a' + 1)
        } while (nuevaPosicion == null || ((nuevaPosicion.getFila() + (nuevaPosicion.getColumna() - 'a' + 1)) % 2 != 0)); // Asegura casilla negra
        // Revisar la lógica de casilla negra según el diagrama de ajedrez (la imagen muestra (1,a) como negra)
        // en la imagen, las casillas con fichas son negras.
        // (1,A) negra, (1,B) blanca, (1,C) negra, (1,D) blanca, (1,E) negra, (1,F) blanca, (1,G) negra, (1,H) blanca
        // (2,A) blanca, (2,B) negra, (2,C) blanca, (2,D) negra, (2,E) blanca, (2,F) negra, (2,G) blanca, (2,H) negra
        // Esto significa que una casilla (fila, columna) es negra si (fila + columna_indice) es par.
        // columna_indice para 'a'=1, 'b'=2...
        // Es decir, (fila + (columna - 'a' + 1)) % 2 == 0

        return nuevaPosicion;
    }


    // Método mover
    public void mover(Direccion direccion, int pasos) {
        if (direccion == null) {
            throw new IllegalArgumentException("ERROR: La dirección no puede ser nula.");
        }
        if (pasos < 1) {
            throw new IllegalArgumentException("ERROR: El número de pasos debe ser mayor o igual a 1.");
        }

        // Guardar la posición actual para restaurar si el movimiento no es válido
        Posicion posicionOriginal = new Posicion(this.posicion);
        int nuevaFila = this.posicion.getFila();
        char nuevaColumna = this.posicion.getColumna();

        // Lógica de movimiento según la dirección
        switch (direccion) {
            case NORESTE:
                nuevaFila += pasos;
                nuevaColumna += pasos;
                break;
            case SURESTE:
                nuevaFila -= pasos;
                nuevaColumna += pasos;
                break;
            case SUROESTE:
                nuevaFila -= pasos;
                nuevaColumna -= pasos;
                break;
            case NOROESTE:
                nuevaFila += pasos;
                nuevaColumna -= pasos;
                break;
        }

        // 1. Validar el movimiento antes de intentar crear la nueva posición
        if (!esDamaEspecial) {
            // Dama NO especial: solo 1 paso y en dirección de avance
            if (pasos != 1) {
                try {
                    throw new OperationNotSupportedException("ERROR: Una dama normal solo puede moverse 1 casilla.");
                } catch (OperationNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (color == Color.BLANCO && (direccion != Direccion.NORESTE && direccion != Direccion.NOROESTE)) {
                try {
                    throw new OperationNotSupportedException("ERROR: Una dama blanca normal solo puede moverse NORESTE o NOROESTE.");
                } catch (OperationNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (color == Color.NEGRO && (direccion != Direccion.SURESTE && direccion != Direccion.SUROESTE)) {
                try {
                    throw new OperationNotSupportedException("ERROR: Una dama negra normal solo puede moverse SURESTE o SUROESTE.");
                } catch (OperationNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // 2. Intentar crear la nueva posición y validar si se sale del tablero
        Posicion posicionTentativa;
        try {
            posicionTentativa = new Posicion(nuevaFila, nuevaColumna);
            // 3. Validar si la nueva posición es una casilla negra (las damas solo se mueven en casillas negras)
            // (fila + (columna - 'a' + 1)) % 2 == 0 para casillas negras
            if (((posicionTentativa.getFila() + (posicionTentativa.getColumna() - 'a' + 1)) % 2 != 0)) {
                throw new OperationNotSupportedException("ERROR: No se puede mover a una casilla blanca.");
            }
        } catch (IllegalArgumentException e) {
            // La nueva fila/columna está fuera de los límites del tablero
            try {
                throw new OperationNotSupportedException("ERROR: El movimiento saca a la dama fuera del tablero.");
            } catch (OperationNotSupportedException ex) {
                throw new RuntimeException(ex);
            }
        } catch (OperationNotSupportedException e) {
            throw new RuntimeException(e);
        }

        // Si todas las validaciones anteriores pasaron, se actualiza la posición
        setPosicion(posicionTentativa);

        // Comprobar si se convierte en dama especial
        if (!esDamaEspecial) { // Solo si no era ya especial
            if ((color == Color.BLANCO && this.posicion.getFila() == Posicion.MAX_FILA) ||
                    (color == Color.NEGRO && this.posicion.getFila() == Posicion.MIN_FILA)) {
                setEsDamaEspecial(true);
            }
        }
    }

    // Método toString
    @Override
    public String toString() {
        return String.format("%s en (%s)%s",
                color.toString(),
                posicion.toString(),
                esDamaEspecial ? " (Dama Especial)" : "");
    }
}
