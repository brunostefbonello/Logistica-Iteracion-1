package Iteracion;

import java.time.LocalDateTime;

public class NodoDeposito {
    private int idDeposito;
    private boolean visitado;
    private LocalDateTime fechaUltimaAuditoria;
    private CentroDeDistribucion centro; 
    private NodoDeposito izquierdo;
    private NodoDeposito derecho;

    public NodoDeposito(int idDeposito, LocalDateTime fechaUltimaAuditoria) {
        this.idDeposito = idDeposito;
        this.fechaUltimaAuditoria = fechaUltimaAuditoria;
        this.visitado = false;
        this.centro = new CentroDeDistribucion(); 
        this.izquierdo = null;
        this.derecho = null;
    }

    public int getIdDeposito() { return idDeposito; }
    public boolean isVisitado() { return visitado; }
    public void setVisitado(boolean visitado) { this.visitado = visitado; }
    public LocalDateTime getFechaUltimaAuditoria() { return fechaUltimaAuditoria; }
    public CentroDeDistribucion getCentro() { return centro; }
    public NodoDeposito getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoDeposito izquierdo) { this.izquierdo = izquierdo; }
    public NodoDeposito getDerecho() { return derecho; }
    public void setDerecho(NodoDeposito derecho) { this.derecho = derecho; }
}