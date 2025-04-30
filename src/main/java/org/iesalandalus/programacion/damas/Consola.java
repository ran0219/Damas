package org.iesalandalus.programacion.damas; // Según el diagrama, Consola está en .vista

import org.iesalandalus.programacion.damas.modelo.Color;
import org.iesalandalus.programacion.damas.modelo.Direccion;
import org.iesalandalus.programacion.utilidades.Entrada; // Clase Entrada del JAR

public class Consola {

    // Constructor privado para evitar instanciación
    private Consola() {
        // No se permite instanciar una clase de utilidades
    }

    // Métodos estáticos

    public static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Crear dama por defecto (Blanca)");
        System.out.println("2. Crear dama eligiendo el color");
        System.out.println("3. Mover dama");
        System.out.println("4. Mostrar información de la dama");
        System.out.println("5. Salir");
        System.out.println("----------------------");
    }

    public static int elegirOpcionMenu() {
        int opcion;
        do {
            System.out.print("Introduce una opción del menú: ");
            opcion = Entrada.entero();
            if (opcion < 1 || opcion > 5) { // Suponiendo 5 opciones en total
                System.out.println("ERROR: Opción no válida. Introduce un número entre 1 y 5.");
            }
        } while (opcion < 1 || opcion > 5);
        return opcion;
    }

    public static Color elegirColor() {
        int opcionColor;
        do {
            System.out.println("\n--- Elige un color ---");
            System.out.println("1. Blanco");
            System.out.println("2. Negro");
            System.out.print("Introduce el número del color: ");
            opcionColor = Entrada.entero();
            if (opcionColor < 1 || opcionColor > 2) {
                System.out.println("ERROR: Opción de color no válida. Introduce 1 para Blanco o 2 para Negro.");
            }
        } while (opcionColor < 1 || opcionColor > 2);

        return (opcionColor == 1) ? Color.BLANCO : Color.NEGRO;
    }

    public static void mostrarMenuDirecciones() {
        System.out.println("\n--- Elige una dirección ---");
        System.out.println("1. Noreste");
        System.out.println("2. Sureste");
        System.out.println("3. Suroeste");
        System.out.println("4. Noroeste");
        System.out.println("---------------------------");
    }

    public static Direccion elegirDireccion() {
        int opcionDireccion;
        Direccion direccionElegida = null;
        do {
            System.out.print("Introduce el número de la dirección: ");
            opcionDireccion = Entrada.entero();
            switch (opcionDireccion) {
                case 1:
                    direccionElegida = Direccion.NORESTE;
                    break;
                case 2:
                    direccionElegida = Direccion.SURESTE;
                    break;
                case 3:
                    direccionElegida = Direccion.SUROESTE;
                    break;
                case 4:
                    direccionElegida = Direccion.NOROESTE;
                    break;
                default:
                    System.out.println("ERROR: Opción de dirección no válida. Introduce un número entre 1 y 4.");
                    break;
            }
        } while (direccionElegida == null); // Repite hasta que se elija una dirección válida
        return direccionElegida;
    }

    public static int elegirPasos() {
        int pasos;
        do {
            System.out.print("Introduce el número de casillas a mover (>= 1): ");
            pasos = Entrada.entero();
            if (pasos < 1) {
                System.out.println("ERROR: El número de pasos debe ser mayor o igual a 1.");
            }
        } while (pasos < 1);
        return pasos;
    }

    public static void despedirse() {
        System.out.println("\n¡Gracias por jugar! ¡Hasta pronto!");
    }
}