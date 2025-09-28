package act11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		String nombreSO = System.getProperty("os.name").toLowerCase();
		Scanner scanner = new Scanner(System.in);
		int opcion;
		String[] comando;
		String destino;

		System.out.println(nombreSO);

		do {
			System.out.println("\n===== MENÚ PRINCIPAL =====");
			System.out.println("1. Hacer Ping");
			System.out.println("2. Listar directorio");
			System.out.println("3. Leer procesos del sistema");
			System.out.println("4. Abrir URL");
			System.out.println("5. Salir");
			System.out.print("Seleccione una opción: ");

			opcion = scanner.nextInt();
			scanner.nextLine();

			switch (opcion) {
			case 1:
				System.out.println("Elige el destino del ping: ");
				destino = scanner.nextLine();

				if (nombreSO.contains("win")) {
					comando = new String[] { "ping", "-n", "4", destino };
				} else {
					comando = new String[] { "ping", "-c", "4", destino };
				}

				try {
					ProcessBuilder pb = new ProcessBuilder(comando);
					pb.redirectErrorStream(true);
					Process proceso = pb.start();

					try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {

						String linea;
						while ((linea = reader.readLine()) != null) {
							System.out.println(linea);
						}
					}

					int exitCode = proceso.waitFor();
					System.out.println("\nProceso finalizado con código: " + exitCode);

				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}

				break;

			case 2:

				System.out.print("Introduce la ruta del directorio: ");
				String rutaDirectorio = scanner.nextLine();

				if (nombreSO.contains("win")) {
					comando = new String[] { "cmd", "/c", "dir", rutaDirectorio };
				} else {
					comando = new String[] { "ls", "-l", rutaDirectorio };
				}

				try {
					ProcessBuilder pb = new ProcessBuilder(comando);
					pb.redirectErrorStream(true);
					Process proceso = pb.start();

					try (BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
						String linea;
						System.out.println("\n===== LISTA DE ARCHIVOS =====");
						while ((linea = reader.readLine()) != null) {
							System.out.println(linea);
						}
					}

					int exitCode = proceso.waitFor();
					System.out.println("\nProceso finalizado con código: " + exitCode);

				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
				break;

			case 3:

				String[] comandoListar;

				if (nombreSO.contains("win")) {
					comandoListar = new String[] { "cmd", "/c", "tasklist" };
				} else {
					comandoListar = new String[] { "ps", "-e" };
				}

				try {

					ProcessBuilder pbList = new ProcessBuilder(comandoListar);
					pbList.redirectErrorStream(true);
					Process procesoList = pbList.start();

					System.out.println("\n===== PROCESOS ACTUALES =====");
					try (BufferedReader reader = new BufferedReader(
							new InputStreamReader(procesoList.getInputStream()))) {
						String linea;
						while ((linea = reader.readLine()) != null) {
							System.out.println(linea);
						}
					}
					procesoList.waitFor();

					System.out.print("\nIntroduce el PID del proceso a cerrar: ");
					destino = scanner.nextLine().trim();

					String[] comandoCerrar;
					if (nombreSO.contains("win")) {

						comandoCerrar = new String[] { "cmd", "/c", "taskkill", "/PID", destino, "/F" };
					} else {

						comandoCerrar = new String[] { "kill", "-9", destino };
					}

					ProcessBuilder pbKill = new ProcessBuilder(comandoCerrar);
					pbKill.redirectErrorStream(true);
					Process procesoKill = pbKill.start();

					try (BufferedReader reader = new BufferedReader(
							new InputStreamReader(procesoKill.getInputStream()))) {
						String linea;
						while ((linea = reader.readLine()) != null) {
							System.out.println(linea);
						}
					}

					int exitCode = procesoKill.waitFor();
					System.out.println("\nProceso de cierre finalizado con código: " + exitCode);

				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
				break;

			case 4:

				System.out.print("Introduce la URL: ");
				destino = scanner.nextLine().trim();

				if (nombreSO.contains("win")) {
					comando = new String[] { "rundll32", "url.dll,FileProtocolHandler", destino };
				} else {
					comando = new String[] { "xdg-open", destino };
				}

				try {
					ProcessBuilder pb = new ProcessBuilder(comando);
					pb.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

			case 5:

				System.out.println("Saliendo del programa.");
				break;

			default:
				System.out.println("Opción no válida.");
			}

		} while (opcion != 5);

		scanner.close();
	}
}
