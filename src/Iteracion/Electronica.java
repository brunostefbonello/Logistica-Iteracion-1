package Iteracion;

public class Electronica {

    private String descripcion;

    public Electronica(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Electrónica: " + descripcion;
    }
}