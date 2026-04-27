package Iteracion;

public class Paquete<T> {
	private String id;
	private double peso;
	private String destino;
	private boolean urgente;
	private T contenido;
	
	public Paquete(String id, double peso, String destino, boolean urgente, T contenido) {
		this.id = id;
		this.peso = peso;
		this.destino = destino;
		this.urgente = urgente;
		this.contenido = contenido;
	}

	public String getId() {
		return id;
	}

	public double getPeso() {
		return peso;
	}

	public String getDestino() {
		return destino;
	}

	public boolean isUrgente() {
		return urgente;
	}

	public T getContenido() {
		return contenido;
	}
}
