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
                    
                    boolean urgente;

                    while (true) {
                        System.out.print("¿Es urgente? (si/no): ");
                        String input = teclado.nextLine().trim().toLowerCase();

                        if (input.equals("si")) {
                            urgente = true;
                            break;
                        } else if (input.equals("no")) {
                            urgente = false;
                            break;
                        } else {
                            System.out.println("Entrada inválida. Escribí 'si' o 'no'.");
                        }
                    }
                    
                    int tipoContenido = 0;

                    while (true) {
                        System.out.println("Tipo de contenido:");
                        System.out.println("1. Electrónica");
                        System.out.println("2. Alimento");
                        System.out.println("3. Frágil");
                        System.out.print("Elegí una opción: ");

                        if (teclado.hasNextInt()) {
                            tipoContenido = teclado.nextInt();
                            teclado.nextLine();

                            if (tipoContenido >= 1 && tipoContenido <= 3) {
                                break;
                            } else {
                                System.out.println("Opción inválida. Elegí 1, 2 o 3.");
                            }
                        } else {
                            System.out.println("Entrada inválida. Tenés que ingresar un número entre el 1 y el 3.");
                            teclado.nextLine();
                        }
                    }
                    
                    System.out.print("Descripción del contenido: ");
                    String descripcion = teclado.nextLine();

                    Paquete<?> nuevo = null;

                    switch (tipoContenido) {
                        case 1:
                            nuevo = new Paquete<Electronica>(
                                    id,
                                    peso,
                                    destino,
                                    urgente,
                                    new Electronica(descripcion)
                            );
                            break;

                        case 2:
                            nuevo = new Paquete<Alimento>(
                                    id,
                                    peso,
                                    destino,
                                    urgente,
                                    new Alimento(descripcion)
                            );
                            break;

                        case 3:
                            nuevo = new Paquete<Fragil>(
                                    id,
                                    peso,
                                    destino,
                                    urgente,
                                    new Fragil(descripcion)
                            );
                            break;
                    }

                    if (nuevo != null) {
                        centro.recibirPaquete(nuevo);
                    }
                    break;

                case 3:
                    System.out.println("\n--- PROCESANDO DESPACHO ---");

                    List<Paquete<?>> zonaPreEmbarque = new ArrayList<>();
                    Paquete<?> listo = centro.procesarSiguientePaquete();

                    while (listo != null) {
                        zonaPreEmbarque.add(listo);
                        listo = centro.procesarSiguientePaquete();
                    }

                    if (zonaPreEmbarque.isEmpty()) {
                        System.out.println("No hay paquetes para despachar.");
                        break;
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
