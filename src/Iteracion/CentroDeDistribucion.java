package Iteracion;

import java.util.HashSet;
import java.util.PriorityQueue;

public class CentroDeDistribucion {
//cola que usa componenet de prioridad
    private PriorityQueue<Paquete<?>> filaPaquetes;
    private HashSet<String> idsRegistrados;

    public CentroDeDistribucion() {
        this.idsRegistrados = new HashSet<>();

        this.filaPaquetes = new PriorityQueue<>((p1, p2) -> {
            boolean p1EsPrioridad = p1.isUrgente() || p1.getPeso() > 50;
            boolean p2EsPrioridad = p2.isUrgente() || p2.getPeso() > 50;

            if (p1EsPrioridad && !p2EsPrioridad) {
                return -1;
            } else if (!p1EsPrioridad && p2EsPrioridad) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    public void recibirPaquete(Paquete<?> nuevoPaquete) {
        if (nuevoPaquete == null) {
            System.out.println("Error: Se intentó ingresar un paquete nulo.");
            return;
        }
//trim elimina espacios en blanco
        if (nuevoPaquete.getId() == null || nuevoPaquete.getId().trim().isEmpty()
                || nuevoPaquete.getDestino() == null || nuevoPaquete.getDestino().trim().isEmpty()
                || nuevoPaquete.getContenido() == null) {

            System.out.println("Error: El paquete tiene datos incompletos.");
            return;
        }

        if (nuevoPaquete.getPeso() < 0) {
            System.out.println("Error: El peso del paquete " + nuevoPaquete.getId() + " no puede ser negativo.");
            return;
        }

        if (idsRegistrados.contains(nuevoPaquete.getId())) {
            System.out.println("Error: El paquete con ID " + nuevoPaquete.getId() + " ya existe en el sistema.");
            return;
        }

        filaPaquetes.add(nuevoPaquete);

        idsRegistrados.add(nuevoPaquete.getId());

        System.out.println("Paquete " + nuevoPaquete.getId() + " ingresado al sistema.");
    }
//poll saca y devuelve el ultimo elemento ingresado a la cola
    public Paquete<?> procesarSiguientePaquete() {
        if (!filaPaquetes.isEmpty()) {
            return filaPaquetes.poll();
        }

        return null;
    }
}
