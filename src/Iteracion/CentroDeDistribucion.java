package Iteracion;

import java.util.PriorityQueue;

public class CentroDeDistribucion {
	private PriorityQueue<Paquete<?>> filaPaquetes;
	
	public CentroDeDistribucion() {
        this.filaPaquetes = new PriorityQueue<>((p1, p2) -> {
            boolean p1EsPrioridad = p1.isUrgente() || p1.getPeso() > 50;
            
            boolean p2EsPrioridad = p2.isUrgente() || p2.getPeso() > 50;

            if (p1EsPrioridad && !p2EsPrioridad) {
                return -1; 
            } 
            else if (!p1EsPrioridad && p2EsPrioridad) {
                return 1; 
            }
            else {
                return 0; 
            }
        });
	}
        public void recibirPaquete(Paquete<?> nuevoPaquete) {
            filaPaquetes.add(nuevoPaquete);
            System.out.println("Paquete " + nuevoPaquete.getId() + " ingresado al sistema.");
        }
        
        public Paquete<?> procesarSiguientePaquete() {
            if (!filaPaquetes.isEmpty()) {
                return filaPaquetes.poll();
            } else {
                System.out.println("No hay paquetes para procesar.");
                return null;
            }
        }
}

