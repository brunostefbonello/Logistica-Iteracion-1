package Iteracion;

import java.util.*;

public class MapaRutas {

    private static class Arista {
        int idDestino;
        double distancia;

        public Arista(int idDestino, double distancia) {
            this.idDestino = idDestino;
            this.distancia = distancia;
        }
    }

    private static class NodoDistancia implements Comparable<NodoDistancia> {
        int idDeposito;
        double distanciaAcumulada;

        public NodoDistancia(int idDeposito, double distanciaAcumulada) {
            this.idDeposito = idDeposito;
            this.distanciaAcumulada = distanciaAcumulada;
        }

        @Override
        public int compareTo(NodoDistancia otro) {
            return Double.compare(this.distanciaAcumulada, otro.distanciaAcumulada);
        }
    }

    private Map<Integer, List<Arista>> adyacencia;

    public MapaRutas() {
        this.adyacencia = new HashMap<>();
    }

    public void agregarDeposito(int idDeposito) {
        adyacencia.putIfAbsent(idDeposito, new ArrayList<>());
    }

    public void agregarRuta(int idOrigen, int idDestino, double distancia) {
        agregarDeposito(idOrigen);
        agregarDeposito(idDestino);
        adyacencia.get(idOrigen).add(new Arista(idDestino, distancia));
        adyacencia.get(idDestino).add(new Arista(idOrigen, distancia));
    }

    /**
     * Algoritmo de Dijkstra que retorna la lista de IDs formando la ruta óptima.
     * Complejidad: O(E log V) donde E son las aristas y V los vértices.
     */
    private List<Integer> calcularRutaMinima(int idOrigen, int idDestino) {
        if (!adyacencia.containsKey(idOrigen) || !adyacencia.containsKey(idDestino)) {
            return Collections.emptyList();
        }

        Map<Integer, Double> distancias = new HashMap<>();
        Map<Integer, Integer> previos = new HashMap<>();
        PriorityQueue<NodoDistancia> cola = new PriorityQueue<>();
        Set<Integer> visitados = new HashSet<>();

        for (Integer deposito : adyacencia.keySet()) {
            distancias.put(deposito, Double.MAX_VALUE);
        }

        distancias.put(idOrigen, 0.0);
        cola.add(new NodoDistancia(idOrigen, 0.0));

        while (!cola.isEmpty()) {
            int actual = cola.poll().idDeposito;

            if (actual == idDestino) break;
            if (visitados.contains(actual)) continue;
            visitados.add(actual);

            for (Arista ruta : adyacencia.get(actual)) {
                int vecino = ruta.idDestino;
                if (visitados.contains(vecino)) continue;

                double nuevaDist = distancias.get(actual) + ruta.distancia;
                if (nuevaDist < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDist);
                    previos.put(vecino, actual);
                    cola.add(new NodoDistancia(vecino, nuevaDist));
                }
            }
        }

        if (distancias.get(idDestino) == Double.MAX_VALUE) return Collections.emptyList();

        List<Integer> ruta = new ArrayList<>();
        int paso = idDestino;
        while (paso != idOrigen) {
            ruta.add(paso);
            paso = previos.get(paso);
        }
        ruta.add(idOrigen);
        Collections.reverse(ruta);
        return ruta;
    }

    /**
     * Simula el recorrido del camión por el mapa interactuando con la clase Camion.
     * Muestra la ruta de Dijkstra y simula la entrega.
     * Complejidad: O(E log V) por la llamada a Dijkstra, más O(R) donde R es la longitud de la ruta.
     */
    public void simularViaje(Camion camion, int idOrigen, int idDestino) {
        List<Integer> ruta = calcularRutaMinima(idOrigen, idDestino);
        
        if (ruta.isEmpty()) {
            System.out.println("No se encontró una ruta válida entre " + idOrigen + " y " + idDestino);
            return;
        }

        System.out.println("\n--- INICIANDO VIAJE LOGÍSTICO ---");
        System.out.println("Ruta calculada: " + ruta);
        
        for (int i = 0; i < ruta.size(); i++) {
            int nodoActual = ruta.get(i);
            System.out.println("\n[Camión en tránsito] -> Llegando al Depósito " + nodoActual);
            
            // Si llegamos al destino, procedemos a descargar la pila del camión
            if (nodoActual == idDestino) {
                System.out.println("¡Destino alcanzado! Iniciando descarga usando la Pila del camión...");
                Paquete<?> entregado = camion.descargarPaquete();
                while (entregado != null) {
                    System.out.println(" -> Paquete " + entregado.getId() + " entregado exitosamente.");
                    entregado = camion.descargarPaquete();
                }
            } else {
                System.out.println("   (Punto de control superado, continuando ruta...)");
            }
        }
        System.out.println("--- VIAJE FINALIZADO ---");
    }
}