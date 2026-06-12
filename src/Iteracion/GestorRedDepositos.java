package Iteracion;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class GestorRedDepositos {
    private ArbolDepositos arbol;

    public GestorRedDepositos() {
        this.arbol = new ArbolDepositos();
    }

    public void insertar(int idDeposito, LocalDateTime fechaUltimaAuditoria) {
        arbol.insertar(idDeposito, fechaUltimaAuditoria);
    }

    public void reportePorNivel(int nivelObjetivo) {
        arbol.reportePorNivel(nivelObjetivo);
    }

    public void auditarDepositos() {
        System.out.println("\n--- Iniciando Auditoría Regional (Post-Orden) ---");
        auditarPostOrdenRecursivo(arbol.getRaiz());
        System.out.println("Auditoría finalizada.\n");
    }

    private void auditarPostOrdenRecursivo(NodoDeposito nodo) {
        if (nodo != null) {
            auditarPostOrdenRecursivo(nodo.getIzquierdo()); 
            auditarPostOrdenRecursivo(nodo.getDerecho());   

            long diasDesdeAuditoria = ChronoUnit.DAYS.between(nodo.getFechaUltimaAuditoria(), LocalDateTime.now());
            if (diasDesdeAuditoria > 30) {
                nodo.setVisitado(true);
                System.out.println("Depósito " + nodo.getIdDeposito() + " marcado como VISITADO. (Última auditoría hace " + diasDesdeAuditoria + " días). Cola de paquetes activa.");
            }
        }
    }
}