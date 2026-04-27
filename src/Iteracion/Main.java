package Iteracion;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CentroDeDistribucion centro = new CentroDeDistribucion();
        Camion camion = new Camion("AE-789-XX");
        
        boolean salir = false;

        System.out.println("SISTEMA DE LOGÍSTICA");

        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Cargar inventario desde JSON");
            System.out.println("2. Ingresar paquete manualmente");
            System.out.println("3. Despachar paquetes (Centro -> Camión)");
            System.out.println("4. Entregar paquetes (Descargar Camión)");
            System.out.println("0. Salir");
            System.out.print("Elegí una opción: ");

            int opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    System.out.println("\nCargando datos...");
                    Paquete<?>[] desdeJson = GestorArchivo.leerInventario();
                    for (Paquete<?> p : desdeJson) {
                        centro.recibirPaquete(p);
                    }
                    System.out.println("¡Inventario cargado exitosamente!");
                    break;

                case 2:
                    System.out.print("ID del paquete: ");
                    String id = teclado.nextLine();
                    System.out.print("Peso: ");
                    double peso = teclado.nextDouble();
                    teclado.nextLine(); 
                    System.out.print("Destino: ");
                    String destino = teclado.nextLine();
                    System.out.print("¿Es urgente? (true/false): ");
                    boolean urgente = teclado.nextBoolean();
                    teclado.nextLine();

                    Paquete<String> nuevo = new Paquete<>(id, peso, destino, urgente, "Carga manual");
                    centro.recibirPaquete(nuevo);
                    break;

                case 3:
                    System.out.println("\n--- PROCESANDO DESPACHO ---");
                    List<Paquete<?>> zonaPreEmbarque = new ArrayList<>();
                    Paquete<?> listo = centro.procesarSiguientePaquete();
                    
                    while (listo != null) {
                        zonaPreEmbarque.add(listo);
                        listo = centro.procesarSiguientePaquete();
                    }

                    for (int i = zonaPreEmbarque.size() - 1; i >= 0; i--) {
                        camion.cargarPaquete(zonaPreEmbarque.get(i));
                    }
                    System.out.println("Camión cargado y listo para salir.");
                    break;

                case 4:
                    System.out.println("\n--- ENTREGANDO PAQUETES ---");
                    Paquete<?> entregado = camion.descargarPaquete();
                    while (entregado != null) {
                        System.out.println("Entregado: " + entregado.getId() + " en " + entregado.getDestino());
                        entregado = camion.descargarPaquete();
                    }
                    break;

                case 0:
                    System.out.println("Cerrando sistema. ¡Buen viaje!");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
        teclado.close();
    }
}