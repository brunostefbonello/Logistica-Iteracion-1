package Iteracion;

import java.util.*;

public class GrafoRutas {
    private Map<Integer, List<Ruta>> adyacencia;
    // NUEVO: Diccionario de equivalencias (Nodo -> Ciudad)
    private Map<Integer, DestinoPermitido> ciudadesAsignadas;

    public GrafoRutas() {
        this.adyacencia = new HashMap<>();
        this.ciudadesAsignadas = new HashMap<>();
    }

    public void agregarDeposito(int idDeposito) {
        adyacencia.putIfAbsent(idDeposito, new ArrayList<>());
    }

    public void agregarRuta(int idOrigen, int idDestino, double distancia) {
        agregarDeposito(idOrigen);
        agregarDeposito(idDestino);
        adyacencia.get(idOrigen).add(new Ruta(idDestino, distancia));
        adyacencia.get(idDestino).add(new Ruta(idOrigen, distancia));
    }

    // NUEVO: Métodos para asignar y consultar la ciudad de un nodo
    public void asignarCiudad(int idDeposito, DestinoPermitido ciudad) {
        ciudadesAsignadas.put(idDeposito, ciudad);
    }

    public DestinoPermitido getCiudad(int idDeposito) {
        return ciudadesAsignadas.get(idDeposito);
    }

    public Map<Integer, List<Ruta>> getAdyacencia() {
        return adyacencia;
    }

    public List<Ruta> getRutasDesde(int idDeposito) {
        return adyacencia.getOrDefault(idDeposito, Collections.emptyList());
    }
}