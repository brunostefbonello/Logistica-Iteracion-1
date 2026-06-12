package Iteracion;

import java.util.List;

public interface EstrategiaRuta {
    List<Integer> calcularRutaMinima(GrafoRutas grafo, int idOrigen, int idDestino);
}