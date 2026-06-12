package Iteracion;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        // --- 1. Instancias base --- 
        CentroDeDistribucion centro = new CentroDeDistribucion();
        Camion camion = new Camion("AE-789-XX");
        
        // --- 2. Instancias del Árbol Binario (Refactorizadas) ---
        GestorRedDepositos redDepositos = new GestorRedDepositos();
        
        // --- 3. Instancias del Grafo y Rutas (Refactorizadas) ---
        GrafoRutas grafoRutas = new GrafoRutas();
        EstrategiaRuta algoritmoDijkstra = new CalculadorDijkstra();
        SimuladorLogistico simulador = new SimuladorLogistico(algoritmoDijkstra);
        
     // --- POBLACIÓN DE DATOS DE PRUEBA ---
        redDepositos.insertar(50, LocalDateTime.now().minusDays(10)); 
        redDepositos.insertar(30, LocalDateTime.now().minusDays(45)); 
        redDepositos.insertar(70, LocalDateTime.now().minusDays(5));  
        redDepositos.insertar(20, LocalDateTime.now().minusDays(35)); 
        redDepositos.insertar(40, LocalDateTime.now().minusDays(15)); 
        redDepositos.insertar(60, LocalDateTime.now().minusDays(60)); 
        redDepositos.insertar(80, LocalDateTime.now().minusDays(2));  

        // ---> ESTO FALTABA: LAS RUTAS FÍSICAS <---
        grafoRutas.agregarRuta(1, 2, 50.5);
        grafoRutas.agregarRuta(1, 3, 20.0);
        grafoRutas.agregarRuta(3, 4, 15.5);
        grafoRutas.agregarRuta(2, 4, 10.0);
        grafoRutas.agregarRuta(4, 5, 30.0);
        grafoRutas.agregarRuta(2, 5, 80.0);

        // --- ASIGNACIÓN DE CIUDADES A LOS NODOS ---
        grafoRutas.asignarCiudad(1, DestinoPermitido.BUENOS_AIRES);
        grafoRutas.asignarCiudad(2, DestinoPermitido.CORDOBA);
        grafoRutas.asignarCiudad(3, DestinoPermitido.ROSARIO);
        grafoRutas.asignarCiudad(4, DestinoPermitido.MENDOZA);
        grafoRutas.asignarCiudad(5, DestinoPermitido.BUENOS_AIRES);

        boolean salir = false;
        System.out.println("SISTEMA DE LOGÍSTICA");

        while (!salir) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Cargar inventario desde JSON");
            System.out.println("2. Ingresar paquete manualmente");
            System.out.println("3. Despachar paquetes (Centro -> Camión)");
            System.out.println("4. Entregar paquetes manual (Descargar Camión)");
            System.out.println("5. Red de Depósitos: Auditoría Regional (ABB)");
            System.out.println("6. Hoja de Ruta: Simular Viaje de Camión (Grafo)");
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
                    try {
                        System.out.print("ID del paquete: ");
                        String id = teclado.nextLine();
                        System.out.print("Peso: ");
                        double peso = teclado.nextDouble();
                        teclado.nextLine(); 
                        
                        // Validacion estricta de Enum
                        System.out.println("Destinos disponibles: ");
                        for (DestinoPermitido d : DestinoPermitido.values()) {
                            System.out.println("- " + d.name());
                        }
                        System.out.print("Ingrese el Destino: ");
                        String entradaDestino = teclado.nextLine().toUpperCase().trim();
                        DestinoPermitido destinoValido = DestinoPermitido.valueOf(entradaDestino);

                        System.out.print("¿Es urgente? (true/false): ");
                        boolean urgente = teclado.nextBoolean();
                        teclado.nextLine();

                        Paquete<String> nuevo = new Paquete<>(id, peso, destinoValido, urgente, "Carga manual");
                        centro.recibirPaquete(nuevo);
                        
                    } catch (IllegalArgumentException e) {
                        System.out.println("ERROR: Destino o formato incorrecto. El paquete NO fue creado.");
                    } catch (Exception e) {
                        System.out.println("Ocurrió un error inesperado.");
                        teclado.nextLine();
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

                case 5:
                    System.out.println("\n--- GESTIÓN DE RED DE DEPÓSITOS (ÁRBOL BINARIO) ---");
                    redDepositos.auditarDepositos();
                    
                    System.out.print("Ingresá el nivel del árbol para ver los depósitos (ej. 0 para la raíz, 1, 2): ");
                    int nivel = teclado.nextInt();
                    teclado.nextLine();
                    redDepositos.reportePorNivel(nivel);
                    break;

                case 6:
                    System.out.println("\n--- SIMULANDO VIAJE DEL CAMIÓN (DIJKSTRA) ---");
                    System.out.println("Importante: Asegurate de haber cargado el camión (Opción 3) antes de viajar.");
                    System.out.println("Depósitos disponibles en la simulación: 1, 2, 3, 4, 5");
                    System.out.print("Ingresá el ID del depósito de Origen: ");
                    int origen = teclado.nextInt();
                    System.out.print("Ingresá el ID del depósito de Destino: ");
                    int destino = teclado.nextInt();
                    teclado.nextLine();
                    
                    simulador.simularViaje(grafoRutas, camion, origen, destino);
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