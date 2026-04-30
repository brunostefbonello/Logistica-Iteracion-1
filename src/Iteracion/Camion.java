package Iteracion;

import java.util.Stack;

public class Camion {
	private String patente;
	private Stack<Paquete<?>> cajaDelCamion;
	
	public Camion (String patente) {
		this.patente = patente;
		this.cajaDelCamion = new Stack<>();
	}
	//pone un item en la pila
	public void cargarPaquete(Paquete<?> paqueteNuevo) {
		cajaDelCamion.push(paqueteNuevo);
		System.out.println("Paquete " + paqueteNuevo.getId() + " cargado al camión de patente: " + patente);
	}
	//pop saca el de arriba
	public Paquete<?> descargarPaquete() {
		if(!cajaDelCamion.isEmpty()) {
			return cajaDelCamion.pop();
		}
		else {
			return null;
		}
	}
	
	public Paquete<?> deshacerUltimaCarga() {
		if (!cajaDelCamion.isEmpty()) {
			Paquete<?> paqueteSacado = cajaDelCamion.pop();
			System.out.println("Se deshizo la carga. Se bajó el paquete: "+ paqueteSacado.getId());
			return paqueteSacado;
		}
		return null;
	}
}
