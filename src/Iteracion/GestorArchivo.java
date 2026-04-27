package Iteracion;

import java.io.FileReader;
import com.google.gson.Gson;

public class GestorArchivo {
    
    public static Paquete<?>[] leerInventario() {
        try {
            Gson traductor = new Gson();
            FileReader lector = new FileReader("inventario.json");
            
            return traductor.fromJson(lector, Paquete[].class);
            
        } catch (Exception e) {
            System.out.println("No se encontró el inventario o hubo un error.");
            return new Paquete<?>[0];
        }
    }
}