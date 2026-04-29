package Iteracion;

public class Fragil {

    private String descripcion;

    public Fragil(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Fragil: " + descripcion;
    }
}