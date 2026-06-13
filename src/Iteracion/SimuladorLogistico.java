package Iteracion;

import java.util.List;
import java.util.Stack;

public class SimuladorLogistico {
    private EstrategiaRuta calculador;

    public SimuladorLogistico(EstrategiaRuta calculador) {
        this.calculador = calculador;
    }

    public void simularViaje(GrafoRutas grafo, Camion camion, int idOrigen, int idDestino) {
        List<Integer> ruta = calculador.calcularRutaMinima(grafo, idOrigen, idDestino);
        
        if (ruta.isEmpty()) {
            System.out.println("No se encontró una ruta válida entre " + idOrigen + " y " + idDestino);
            return;
        }

        System.out.println("\n--- INICIANDO VIAJE LOGÍSTICO ---");
        System.out.println("Ruta calculada: " + ruta);
        
        for (int i = 0; i < ruta.size(); i++) {
            int nodoActual = ruta.get(i);
            DestinoPermitido ciudadActual = grafo.getCiudad(nodoActual);
            String nombreCiudad = (ciudadActual != null) ? ciudadActual.name() : "Desconocida";

            System.out.println("\n[Camión en tránsito] -> Llegando al Depósito " + nodoActual + " (" + nombreCiudad + ")");
            
            if (nodoActual == idDestino) {
                System.out.println("¡Destino alcanzado! Iniciando descarga inteligente...");
                
                Stack<Paquete<?>> paquetesRetenidos = new Stack<>();
                Paquete<?> entregado = camion.descargarPaquete();
                
                while (entregado != null) {
         
                    if (entregado.getDestino() == ciudadActual) {
                        System.out.println(" -> ÉXITO: Paquete " + entregado.getId() + " entregado en " + nombreCiudad);
                    } else {
                        System.out.println(" -> RETENIDO: Paquete " + entregado.getId() + " es para " + entregado.getDestino() + ". Se queda en el camión.");
                        paquetesRetenidos.push(entregado);
                    }
                    entregado = camion.descargarPaquete();
                }

                
                if (!paquetesRetenidos.isEmpty()) {
                    System.out.println("\n[Reorganizando camión con paquetes no entregados...]");
                    while (!paquetesRetenidos.isEmpty()) {
                        camion.cargarPaquete(paquetesRetenidos.pop());
                    }
                }
            } else {
                System.out.println("   (Punto de control superado, continuando ruta...)");
            }
        }
        System.out.println("--- VIAJE FINALIZADO ---");
    }
}