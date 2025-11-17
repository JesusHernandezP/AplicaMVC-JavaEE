package es.modelo;

import java.io.Serializable;

/**
 * 
 * * @author Jesus Hernandez
 */
public class Articulo implements Serializable {
    
    private int id;
    private String categoria;
    private String descripcion;
    private double precio;

    // Constructor 
    public Articulo() {
    }

    // Constructor con todos los campos
    public Articulo(int id, String categoria, String descripcion, double precio) {
        this.id = id;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // --- Getters y Setters ---
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Articulo{" + "id=" + id + ", categoria=" + categoria + ", descripcion=" + descripcion + ", precio=" + precio + '}';
    }
}