package Iteracion;

public class Alimento {

    private String descripcion;

    public Alimento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Alimento: " + descripcion;
    }
}