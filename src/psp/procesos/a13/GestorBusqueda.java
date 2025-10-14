package psp.procesos.a13;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GestorBusqueda {

    static String CLASSNAME = "psp.procesos.a13.BuscadorTexto";
//    static String CLASSPATH = "/home/alumno/Documentos/PSP/A1.3_TEMPLATE/out/production/A1.3/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int opcion;

        do {
            MenuPrincipal();
            System.out.print("Elegir opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> Submenu(scanner);
                case 2 -> System.out.println("Operación cancelada.");
                case 3 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 3);

        scanner.close();
    }

    // ==== MENÚ PRINCIPAL ====
    private static void MenuPrincipal() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Buscar");
        System.out.println("2. Cancelar");
        System.out.println("3. Salir");
    }

    // ==== SUBMENÚ DE BÚSQUEDA ====
    private static void Submenu(Scanner scanner) {
        String palabra;
        int numtext;
        boolean comprobar = false;

        do {
            System.out.println("\n===== MENÚ DE BÚSQUEDA =====");
            System.out.print("Elegir Palabra: ");
            palabra = scanner.nextLine().trim();


            if (palabra.equalsIgnoreCase("casa") || palabra.equalsIgnoreCase("perro") || palabra.equalsIgnoreCase("coche")) {

                System.out.print("Elegir archivo: ");
                numtext = scanner.nextInt();
                scanner.nextLine();

                if (numtext >= 1 && numtext <= 5) {
                    comprobar = true;
                    Process process = null;
                    try {
                        ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), CLASSNAME, palabra, String.valueOf(numtext));
                        pb.redirectErrorStream(true);
                        process = pb.start();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String linea;
                        while ((linea = reader.readLine()) != null) {
                            System.out.println(linea);
                        }

                    } catch (IOException | NullPointerException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("Ejecutando busqueda...");
                }else {
                    System.out.println("Número de archivo no valido");
                }
            }else {
                System.out.println("Palabra no valida");
            }

        } while (!comprobar);
    }

}

