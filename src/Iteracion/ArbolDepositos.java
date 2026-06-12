package Iteracion;

import java.time.LocalDateTime;

public class ArbolDepositos {
    private NodoDeposito raiz;

    public ArbolDepositos() {
        this.raiz = null;
    }

    public NodoDeposito getRaiz() {
        return raiz;
    }

    public void insertar(int idDeposito, LocalDateTime fechaUltimaAuditoria) {
        raiz = insertarRecursivo(raiz, idDeposito, fechaUltimaAuditoria);
    }

    private NodoDeposito insertarRecursivo(NodoDeposito nodo, int idDeposito, LocalDateTime fecha) {
        if (nodo == null) {
            return new NodoDeposito(idDeposito, fecha);
        }
        if (idDeposito < nodo.getIdDeposito()) {
            nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), idDeposito, fecha));
        } else if (idDeposito > nodo.getIdDeposito()) {
            nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), idDeposito, fecha));
        }
        return nodo;
    }

    public void reportePorNivel(int nivelObjetivo) {
        System.out.println("\n--- Reporte de Depósitos en Nivel " + nivelObjetivo + " ---");
        imprimirNivelRecursivo(raiz, 0, nivelObjetivo); 
    }

    private void imprimirNivelRecursivo(NodoDeposito nodo, int nivelActual, int nivelObjetivo) {
        if (nodo == null) return;
        
        if (nivelActual == nivelObjetivo) {
            System.out.println("- Depósito ID: " + nodo.getIdDeposito());
        } else {
            imprimirNivelRecursivo(nodo.getIzquierdo(), nivelActual + 1, nivelObjetivo);
            imprimirNivelRecursivo(nodo.getDerecho(), nivelActual + 1, nivelObjetivo);
        }
    }
}