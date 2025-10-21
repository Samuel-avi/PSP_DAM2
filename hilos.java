package psp.hilos;

public class Main {
    public static void main(String[] args) {

        HiloNombreContador hilo1, hilo2, hilo3;
        hilo1 = new HiloNombreContador();
        hilo1.setName("Mihilo1: ");
        hilo2 = new HiloNombreContador();
        hilo2.setName("Mihilo2: ");
        hilo3 = new HiloNombreContador();
        hilo3.setName("Mihilo3: ");

        hilo1.setPriority(Thread.MIN_PRIORITY);
        hilo2.setPriority(Thread.MAX_PRIORITY);
        hilo3.setPriority(Thread.NORM_PRIORITY);

        System.out.println("Comienza la ejecución...");

        hilo1.start();
        hilo2.start();
        hilo3.start();

        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}


package psp.hilos;

public class HiloNombreContador extends Thread{

    @Override
    public void run()  {

        for (int c = 1; c<=50; c++) {
            System.out.println("hilo " + getName() + "contador " + c);
        }
        try {
            sleep(50);
        }catch (InterruptedException e) {
            throw new RuntimeException();
        }
    }
}

