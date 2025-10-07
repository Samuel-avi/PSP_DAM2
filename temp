package actividad2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Randnum {

    public static void main(String[] args) {
        Random random = new Random();
        String filename = "output.txt";  // Set a valid filename

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            int randomNumber = random.nextInt(10);  // Random number between 0-9
            bw.write(String.valueOf(randomNumber)); // Write as string
            System.out.println("Random number " + randomNumber + " written to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // args[0] = filename;  <-- Not useful here
    }
}




package psp.procesos.sumas;

import com.sun.source.tree.TryTree;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LanzadorSumas {

    private static String FILENAME = "logsumas.txt";

    private void LanzarSumas(Integer n1, Integer n2, Integer proceso) {


        ProcessBuilder pb;
        Process process;

        String classname = "psp.procesos.sumas.Sumador";
        String classpath = "/home/alumno/IdeaProjects/Lanzadorsumas/out/production/Lanzadorsumas";
        String currentPath = System.getProperty("user.dir");

        try {

            pb = new ProcessBuilder("java", "-cp", classpath, classname, n1.toString(), n2.toString(), proceso.toString(), FILENAME);

            pb.inheritIO();
            process = pb.start();

            // process.waitFor();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {

        LanzadorSumas ls = new LanzadorSumas();
        ls.LanzarSumas(2, 3, 1);
        ls.LanzarSumas(24, 323, 2);
        ls.LanzarSumas(123, 234, 3);
        ls.LanzarSumas(22344, 323434, 4);


        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(FILENAME));
            String output, line;
            output = "";

            while (line == br.newLine() != null) {
                output += line;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




package psp.procesos.sumas;

import javax.xml.transform.stream.StreamSource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Sumador {

    public int sumar(int n1, int n2, int proceso){

        int resultado = 0;
        for (int i=n1; i<n2; i++) {
            resultado+=i;

        }

        System.out.println("Ejecutando proceso " + proceso);
        System.out.println("El resultado es " + resultado);
        return resultado;
    }


    public int sumar2(int n1, int n2, int proceso, String filename){

        int resultado = 0;
        for (int i=n1; i<n2; i++) {
            resultado+=i;

        }

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write("Ejecutando proceso " + proceso + "\n");
            bw.write("El resultado es " + resultado);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Ejecutando proceso " + proceso);
        System.out.println();
        return resultado;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

//        int n1 = Integer.parseInt(args[0]);
//        int n2 = Integer.parseInt(args[1]);
//        int proceso = Integer.parseInt(args[2]);
//        String filename = args[3];


        Sumador sumador = new Sumador();
        // sumador.sumar(n1, n2, proceso);

//        sumador.sumar2(n1, n2 ,proceso, filename);


    }
}
