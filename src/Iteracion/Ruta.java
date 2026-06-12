package Iteracion;

public class Ruta {
    private int idDestino;
    private double distancia;

    public Ruta(int idDestino, double distancia) {
        this.idDestino = idDestino;
        this.distancia = distancia;
    }

    public int getIdDestino() { return idDestino; }
    public double getDistancia() { return distancia; }
}