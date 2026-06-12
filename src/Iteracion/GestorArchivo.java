package Iteracion;

import java.io.FileReader;
import com.google.gson.Gson;

public class GestorArchivo {
    
    public static Paquete<?>[] leerInventario() {
        try {
            Gson traductor = new Gson();
            // Asegúrate de que inventario.json esté en la carpeta RAÍZ de tu proyecto (fuera de src)
            FileReader lector = new FileReader("inventario.json");

            return traductor.fromJson(lector, Paquete[].class);

        } catch (Exception e) {
            System.out.println("Error crítico al cargar el JSON: " + e.getMessage());
            e.printStackTrace(); // Esto nos mostrará la línea exacta del error en la consola
            return new Paquete<?>[0];
        }
    }
}