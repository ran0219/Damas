package org.iesalandalus.programacion.damas;

import javax.naming.OperationNotSupportedException;
import org.iesalandalus.programacion.damas.modelo.Color;
import org.iesalandalus.programacion.damas.modelo.Dama;
import org.iesalandalus.programacion.damas.modelo.Direccion;
import org.iesalandalus.programacion.damas.Consola;

public class MainApp {

    private static Dama dama; // Atributo de clase dama

    public static void main(String[] args) {
        int opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcionMenu();
            ejecutarOpcion(opcion);
        } while (opcion != 5); // Suponiendo 5 es la opción de salir
        Consola.despedirse();
    }

    public static void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1:
                    crearDamaDefecto();
                    System.out.println("Dama creada por defecto (Blanca).");
                    break;
                case 2:
                    crearDamaColor();
                    System.out.println("Dama creada con el color elegido.");
                    break;
                case 3:
                    mover();
                    break;
                case 4:
                    mostrarDama();
                    break;
                case 5:
                    // La despedida se maneja en el main
                    break;
                default:
                    System.out.println("ERROR: Opción no contemplada. Por favor, intente de nuevo.");
            }
        } catch (IllegalArgumentException | OperationNotSupportedException e) {
            // Captura excepciones generales que puedan surgir durante la ejecución de las opciones
            System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) { // Captura cualquier otra excepción inesperada
            System.out.println("ERROR INESPERADO: " + e.getMessage());
        }
    }

    private static void crearDamaDefecto() {
        dama = new Dama();
    }

    private static void crearDamaColor() {
        Color colorElegido = Consola.elegirColor();
        dama = new Dama(colorElegido);
    }

    private static void mover() throws OperationNotSupportedException {
        if (dama == null) {
            System.out.println("AVISO: Primero debes crear una dama.");
            return;
        }

        Consola.mostrarMenuDirecciones();
        Direccion direccion = Consola.elegirDireccion();
        int pasos = 1; // Por defecto para dama normal

        if (dama.esDamaEspecial()) {
            pasos = Consola.elegirPasos(); // Pide pasos solo si es especial
        }

        dama.mover(direccion, pasos);
        System.out.println("Dama movida a la nueva posición: " + dama.getPosicion());
        if (dama.esDamaEspecial() && !dama.getPosicion().equals(dama.getPosicion())) { // Simplificado para evitar que se repita el mensaje si ya era especial
            System.out.println("¡La dama se ha convertido en Dama Especial!"); // Solo si acaba de convertirse
        } else if (dama.esDamaEspecial()) {
            System.out.println("¡La dama es especial!");
        }
    }

    private static void mostrarDama() {
        if (dama == null) {
            System.out.println("No hay ninguna dama creada todavía.");
        } else {
            System.out.println("Información de la dama: " + dama);
        }
    }
}