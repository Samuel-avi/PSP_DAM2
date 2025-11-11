import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int empleados = 0;
        int puestos = 0;
        boolean masEmpleadosQuePuestos = false;

        do {
            try {
                System.out.println("Cuantos trabajadores tiene la empresa: ");
                empleados = scanner.nextInt();
                System.out.println("Cuantos puestos y ordenadores tiene la empresa: ");
                puestos = scanner.nextInt();
                masEmpleadosQuePuestos = empleados > puestos;

                if (!masEmpleadosQuePuestos) {
                    System.out.println("El número de empleados debe ser mayor que el número de puestos y ordenadores");
                }

            }catch (InputMismatchException e) {
                System.out.println("El número de empleados y puestos debe ser un número entero");
                scanner.nextLine();
            }

        }while (!masEmpleadosQuePuestos);

        scanner.close();

        Semaphore mesa = new Semaphore(puestos, true);
        Semaphore ordenador = new Semaphore(puestos, true);

        for (int i = 1; i <= empleados; i++) {
            Thread t = new Thread(new Empleado(mesa, ordenador, i));
            t.start();
        }

    }
}
