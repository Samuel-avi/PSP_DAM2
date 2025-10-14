package psp.procesos.a13;

import java.io.File;
import java.io.IOException;

public class BuscadorTexto {

    public BuscadorTexto() {

    }

    public void buscarTexto(String idFichero, String opcionBucar) throws IOException {
        System.out.println("proba");
        File uno = new File("hola.txt");
        uno.createNewFile();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("probados");
        BuscadorTexto no = new BuscadorTexto();
        no.buscarTexto(args[0], args[1]);
    }
    /*IMPORTANTE JAVA VER 17*/
}
