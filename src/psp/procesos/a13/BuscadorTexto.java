package psp.procesos.a13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase que busca una palabra dentro de un archivo de texto.
 *
 * Se le pasa una palabra y un número de fichero (1–5), y muestra
 * cuántas veces aparece la palabra en ese archivo.
 */
public class BuscadorTexto {

    /** Palabra que se quiere buscar. */
    private String palabra;

    /** Número del fichero donde se va a buscar. */
    private int numFichero;

    /**
     * Constructor de la clase. Llama al método que hace la búsqueda.
     *
     * @param palabra palabra que se va a buscar
     * @param numFichero número del fichero (1–5)
     */
    public BuscadorTexto(String palabra, int numFichero) {
        this.palabra = palabra;
        this.numFichero = numFichero;
        try {
            buscarTexto();
        } catch (IOException e) {
            System.out.println("Error al buscar texto: " + e.getMessage());
        }
    }

    /**
     * Busca la palabra en el fichero correspondiente.
     *
     * @throws IOException si ocurre un error al leer el archivo
     */
    public void buscarTexto() throws IOException {
        String nombreFichero = "texto" + numFichero + ".txt";
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            int numLinea = 1;

            while ((linea = br.readLine()) != null) {
                if (linea.toLowerCase().contains(palabra.toLowerCase())) {
                    contador++;
                }
                numLinea++;
            }

            if (contador == 0) {
                System.out.println("\nLa palabra \"" + palabra + "\" no se encontró en " + nombreFichero);
            } else {
                System.out.println("\nLa palabra \"" + palabra + "\" aparece " + contador + " veces en " + nombreFichero);
            }

        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + nombreFichero);
        }
    }

    /**
     * Método principal. Recibe los argumentos desde la línea de comandos.
     *
     * @param args palabra y número de fichero
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java psp.procesos.a13.BuscadorTexto <palabra> <numFichero>");
            return;
        }

        String palabra = args[0];
        int numFichero = Integer.parseInt(args[1]);

        new BuscadorTexto(palabra, numFichero);
    }
}
