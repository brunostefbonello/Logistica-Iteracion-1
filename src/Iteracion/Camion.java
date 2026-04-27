package Iteracion;

import java.util.Stack;

public class Camion {
	private String patente;
	private Stack<Paquete<?>> cajaDelCamion;
	
	public Camion (String patente) {
		this.patente = patente;
		this.cajaDelCamion = new Stack<>();
	}
	
	public void cargarPaquete(Paquete<?> paqueteNuevo) {
		cajaDelCamion.push(paqueteNuevo);
		System.out.println("Paquete " + paqueteNuevo.getId() + " cargado al camión.");
	}
	
	public Paquete<?> descargarPaquete() {
		if(!cajaDelCamion.isEmpty()) {
			return cajaDelCamion.pop();
		}
		else {
			System.out.println("El camion ya esta vacio");
			return null;
		}
	}
	
	public Paquete<?> deshacerUltimaCarga() {
		if (!cajaDelCamion.isEmpty()) {
			Paquete<?> paqueteSacado = cajaDelCamion.pop();
			System.out.println("Carga deshecha. Se bajó el paquete: "+ paqueteSacado.getId());
			return paqueteSacado;
		}
		return null;
	}
}
