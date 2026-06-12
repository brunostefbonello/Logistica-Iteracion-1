package Iteracion;

public class Paquete<T> {
    private String id;
    private double peso;
    private DestinoPermitido destino; 
    private boolean urgente;
    private T contenido;
    
    public Paquete(String id, double peso, DestinoPermitido destino, boolean urgente, T contenido) {
        this.id = id;
        this.peso = peso;
        this.destino = destino;
        this.urgente = urgente;
        this.contenido = contenido;
    }

    public String getId() { return id; }
    public double getPeso() { return peso; }
    public DestinoPermitido getDestino() { return destino; }
    public boolean isUrgente() { return urgente; }
    public T getContenido() { return contenido; }
}