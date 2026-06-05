package Iteracion;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class RedDepositos {

    // Nodo interno encapsulado
    private static class NodoDeposito {
        int idDeposito;
        boolean visitado;
        LocalDateTime fechaUltimaAuditoria;
        CentroDeDistribucion centro; // Integración estricta solicitada
        NodoDeposito izquierdo;
        NodoDeposito derecho;

        public NodoDeposito(int idDeposito, LocalDateTime fechaUltimaAuditoria) {
            this.idDeposito = idDeposito;
            this.fechaUltimaAuditoria = fechaUltimaAuditoria;
            this.visitado = false;
            this.centro = new CentroDeDistribucion(); // Cada depósito nace con su cola de prioridad
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    private NodoDeposito raiz;

    public RedDepositos() {
        this.raiz = null;
    }

    /**
     * Inserta un nuevo depósito en el ABB.
     * Complejidad: O(log N) promedio, O(N) en el peor de los casos (árbol desbalanceado).
     */
    public void insertar(int idDeposito, LocalDateTime fechaUltimaAuditoria) {
        raiz = insertarRecursivo(raiz, idDeposito, fechaUltimaAuditoria);
    }

    private NodoDeposito insertarRecursivo(NodoDeposito nodo, int idDeposito, LocalDateTime fecha) {
        if (nodo == null) {
            return new NodoDeposito(idDeposito, fecha);
        }
        if (idDeposito < nodo.idDeposito) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, idDeposito, fecha);
        } else if (idDeposito > nodo.idDeposito) {
            nodo.derecho = insertarRecursivo(nodo.derecho, idDeposito, fecha);
        }
        return nodo;
    }

    /**
     * Recorre el árbol en Post-Orden (Izquierda, Derecha, Raíz) marcando depósitos sin auditar.
     * Complejidad: O(N) ya que debe visitar todos los nodos del árbol.
     */
    public void auditarDepositos() {
        System.out.println("\n--- Iniciando Auditoría Regional (Post-Orden) ---");
        auditarPostOrdenRecursivo(raiz);
        System.out.println("Auditoría finalizada.\n");
    }

    private void auditarPostOrdenRecursivo(NodoDeposito nodo) {
        if (nodo != null) {
            auditarPostOrdenRecursivo(nodo.izquierdo); // Izquierda
            auditarPostOrdenRecursivo(nodo.derecho);   // Derecha

            // Raíz (Procesamiento)
            long diasDesdeAuditoria = ChronoUnit.DAYS.between(nodo.fechaUltimaAuditoria, LocalDateTime.now());
            if (diasDesdeAuditoria > 30) {
                nodo.visitado = true;
                System.out.println("Depósito " + nodo.idDeposito + " marcado como VISITADO. (Última auditoría hace " + diasDesdeAuditoria + " días). Cola de paquetes activa.");
            }
        }
    }

    /**
     * Imprime los IDs de los depósitos que se encuentran en el Nivel N.
     * Complejidad: O(N) en el peor de los casos, donde N es la cantidad total de nodos.
     */
    public void reportePorNivel(int nivelObjetivo) {
        System.out.println("\n--- Reporte de Depósitos en Nivel " + nivelObjetivo + " ---");
        imprimirNivelRecursivo(raiz, 0, nivelObjetivo); // Asumiendo que la raíz es el Nivel 0
    }

    private void imprimirNivelRecursivo(NodoDeposito nodo, int nivelActual, int nivelObjetivo) {
        if (nodo == null) return;
        
        if (nivelActual == nivelObjetivo) {
            System.out.println("- Depósito ID: " + nodo.idDeposito);
        } else {
            imprimirNivelRecursivo(nodo.izquierdo, nivelActual + 1, nivelObjetivo);
            imprimirNivelRecursivo(nodo.derecho, nivelActual + 1, nivelObjetivo);
        }
    }
}