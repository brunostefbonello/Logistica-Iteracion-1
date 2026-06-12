package Iteracion;

import java.util.*;

public class CalculadorDijkstra implements EstrategiaRuta {

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

    @Override
    public List<Integer> calcularRutaMinima(GrafoRutas grafo, int idOrigen, int idDestino) {
        Map<Integer, List<Ruta>> adyacencia = grafo.getAdyacencia();
        
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

            for (Ruta ruta : grafo.getRutasDesde(actual)) {
                int vecino = ruta.getIdDestino();
                if (visitados.contains(vecino)) continue;

                double nuevaDist = distancias.get(actual) + ruta.getDistancia();
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
}