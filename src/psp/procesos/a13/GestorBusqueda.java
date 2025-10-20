package psp.procesos.a13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que gestiona las búsquedas de palabras en varios ficheros.
 *
 * Permite lanzar procesos para buscar una palabra en un archivo,
 * cancelar todas las búsquedas o salir del programa.
 */
public class GestorBusqueda {

    /** Nombre completo de la clase que se ejecutará en otro proceso. */
    static String CLASSNAME = "psp.procesos.a13.BuscadorTexto";

    /** Indica si un fichero (del 1 al 5) está siendo usado. */
    private static boolean[] ficheroEnUso = new boolean[6];

    /** Lista con los procesos activos. */
    private static final List<Process> procesosActivos = new ArrayList<>();

    /** Scanner para leer datos del usuario. */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Método principal del programa. Muestra el menú y gestiona las opciones del usuario.
     *
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        int opcion;

        do {
            MenuPrincipal();
            System.out.print("Elegir opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> Submenu(scanner);
                case 2 -> cancelarProcesos();
                case 3 -> cerrarPrograma();
                default -> System.out.println("Opción no válida.");
            }

        } while (true);
    }

    /**
     * Muestra el menú principal por pantalla.
     */
    private static void MenuPrincipal() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Buscar");
        System.out.println("2. Cancelar todas las búsquedas");
        System.out.println("3. Salir");
    }

    /**
     * Muestra el submenú para hacer una búsqueda.
     *
     * Pide una palabra y el número del archivo, y lanza un proceso nuevo.
     * Solo se permiten las palabras "casa", "perro" o "coche".
     *
     * @param scanner Objeto Scanner para leer los datos del usuario
     */
    private static void Submenu(Scanner scanner) {
        String palabra;
        int numtext;
        boolean comprobar = false;

        do {
            System.out.println("\n===== MENÚ DE BÚSQUEDA =====");
            System.out.print("Elegir Palabra: ");
            palabra = scanner.nextLine().trim();

            if (palabra.equalsIgnoreCase("casa") ||
                    palabra.equalsIgnoreCase("perro") ||
                    palabra.equalsIgnoreCase("coche")) {

                System.out.print("Elegir archivo (1–5): ");
                numtext = scanner.nextInt();
                scanner.nextLine();

                if (numtext >= 1 && numtext <= 5) {
                    synchronized (GestorBusqueda.class) {
                        if (ficheroEnUso[numtext]) {
                            System.out.println("⚠️  El fichero " + numtext + " ya está siendo buscado.");
                            continue;
                        }
                        ficheroEnUso[numtext] = true;
                    }

                    comprobar = true;
                    try {
                        ProcessBuilder pb = new ProcessBuilder(
                                "java", "-cp",
                                System.getProperty("java.class.path"),
                                CLASSNAME, palabra, String.valueOf(numtext)
                        );
                        pb.redirectErrorStream(true);
                        Process process = pb.start();

                        synchronized (GestorBusqueda.class) {
                            procesosActivos.add(process);
                        }

                        int finalNumtext = numtext;
                        new Thread(() -> {
                            try (BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(process.getInputStream()))) {
                                String linea;
                                while ((linea = reader.readLine()) != null) {
                                    System.out.println(linea);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                synchronized (GestorBusqueda.class) {
                                    ficheroEnUso[finalNumtext] = false;
                                    procesosActivos.remove(process);
                                }
                                System.out.println("\nBúsqueda terminada en fichero " + finalNumtext);
                            }
                        }).start();

                        System.out.println("Ejecutando búsqueda en fichero " + numtext + "...");

                    } catch (IOException e) {
                        System.out.println("Error al lanzar el proceso: " + e.getMessage());
                        synchronized (GestorBusqueda.class) {
                            ficheroEnUso[numtext] = false;
                        }
                    }

                } else {
                    System.out.println("Número de archivo no válido (1 a 5).");
                }
            } else {
                System.out.println("Palabra no válida (solo: casa, perro, coche).");
            }

        } while (!comprobar);
    }

    /**
     * Cancela todos los procesos activos.
     */
    private static void cancelarProcesos() {
        synchronized (GestorBusqueda.class) {
            if (procesosActivos.isEmpty()) {
                System.out.println("No hay procesos activos para cancelar.");
                return;
            }

            System.out.println("Cancelando todos los procesos...");
            for (Process p : procesosActivos) {
                p.destroy();
            }
            procesosActivos.clear();

            for (int i = 1; i <= 5; i++) {
                ficheroEnUso[i] = false;
            }
            System.out.println("Todos los procesos fueron cancelados");
        }
    }

    /**
     * Cierra el programa.
     */
    private static void cerrarPrograma() {
        System.out.println("\nCerrando el programa...");
        System.exit(0);
    }
}
