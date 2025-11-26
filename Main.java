import java.io.IOException;

import java.util.Scanner;

public class Main {

    static String ip= "";
    private static final Scanner scanner = new Scanner(System.in);
    private static final String nombreSO = System.getProperty("os.name").toLowerCase();
    private static final Process[] proceso = new Process[255];
    private static boolean exit = false;
    private static final int[] exitCode = new int[255];


    public static void main(String[] args) {

        while (!validarSubred(ip)) {
            System.out.print("Escribe una dirección x.x.x: ");
            ip = scanner.nextLine().trim();
            if (validarSubred(ip)) {
                System.out.println("ip valida, iniciando procesos...");
            }else {
                System.out.println("ip invalida");
            }
        }

        escanearSubred();
        System.out.println("procesos terminados...");

        for (int i=1;i<255;i++) {
            if (exitCode[i] == 0) {
                System.out.println("IP " + (ip + "." + i) + " ACTIVA");
            }
        }
    }

    public static boolean validarSubred(String ip) {
        // Step 1: Separate the given string into an array of strings using the dot as delimiter
        String[] parts = ip.split("\\.");

        // Step 2: Check if there are exactly 3 parts
        if (parts.length != 3) {
            return false;
        }

        // Step 3: Check each part for valid number
        for (String part : parts) {
            try {
                // Step 4: Convert each part into a number
                int num = Integer.parseInt(part);

                // Step 5: Check whether the number lies in between 0 to 255
                if (num < 0 || num > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                // If parsing fails, it's not a valid number
                return false;
            }
        }

        // If all checks passed, return true
        return true;
    }


    public static void escanearSubred() {
        for (int i=1;i<255;i++) {
            String[] comando;
            if (nombreSO.contains("win")) {
                comando = new String[] { "ping", "-n", "4", (ip+"."+i) };
            } else {
                comando = new String[] { "ping", "-c", "4", (ip+"."+i) };
            }
            try {
                ProcessBuilder pb = new ProcessBuilder(comando);
                pb.redirectErrorStream(true);
                System.out.println("Haciendo ping a ip " + (ip+"."+i) );
                proceso[i] = pb.start();
            } catch (IOException e) {
                e.addSuppressed(e);
            }
        }
        while (!exit) {
            exit = true;
            for (int i = 1; i < 255; i++) {
                if (proceso[i].isAlive()) {
                    exit = false;
                    break;
                }
            }
        }
        for(int i = 1; i < 255; i++) {
            exitCode[i] = proceso[i].exitValue();
        }
    }
}
