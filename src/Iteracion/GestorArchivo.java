package Iteracion;

import java.io.FileReader;
import com.google.gson.Gson;

public class GestorArchivo {
    
    public static Paquete<?>[] leerInventario() {
        try {
            Gson traductor = new Gson();
            FileReader lector = new FileReader("inventario.json");
//inicializa gson y el lecto
            return traductor.fromJson(lector, Paquete[].class);
//fromgson traduce de json a java, (primero de donde sale el json,en que objeto se transforma el texto)
        } catch (Exception e) {
            System.out.println("No se encontró el inventario o hubo un error.");
            return new Paquete<?>[0];
        }
    }
}