import java.util.Random;
import java.util.concurrent.Semaphore;

public class Empleado implements Runnable {

    Random random = new Random();

    private final Semaphore MESA;
    private final Semaphore ORDENADOR;
    private final int id;
    private final int boundTiempo = random.nextInt(1,5000);

    public Empleado(Semaphore mesa, Semaphore ordenador, int id) {
        MESA = mesa;
        ORDENADOR = ordenador;
        this.id = id;
    }

    @Override
    public void run() {

        while (true) {
            try {
                final boolean PRIMERO_MESA = random.nextBoolean();
                if (PRIMERO_MESA) {
                    MESA.acquire();
                    System.out.println("Empleado " + id + " ha reservado una MESA.");
                    ORDENADOR.acquire();
                    System.out.println("Empleado " + id + " ha recogido un ORDENADOR.");
                } else {
                    ORDENADOR.acquire();
                    System.out.println("Empleado " + id + " ha recogido un ORDENADOR.");
                    MESA.acquire();
                    System.out.println("Empleado " + id + " ha reservado una MESA.");
                }

                int TIEMPO_TRABAJO = random.nextInt(0, boundTiempo);
                System.out.println("Empleado " + id + " trabaja " + TIEMPO_TRABAJO + " ms.");
                Thread.sleep(TIEMPO_TRABAJO);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("Empleado " + id + " ha liberado sus recursos.");
                MESA.release();
                ORDENADOR.release();
            }
            try {
                int TIEMPO_DESCANSO = random.nextInt(0, boundTiempo);
                System.out.println("Empleado " + id + " descansa " + TIEMPO_DESCANSO + " ms.");
                Thread.sleep(TIEMPO_DESCANSO);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
