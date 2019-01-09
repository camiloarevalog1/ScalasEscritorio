package com.zapateria.main.dto;

/**
 *
 * @author Diego
 */
public class MaterialDTO implements java.io.Serializable {

    private static final long serialVersionUID = 7337130184410583413L;

    private long id;
    private String nombre;
    private long cantidad;

    public MaterialDTO(long id, String nombre, long cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public MaterialDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public Object[] toArray() {
        return new Object[]{
            id, nombre, cantidad
        };
    }
}
