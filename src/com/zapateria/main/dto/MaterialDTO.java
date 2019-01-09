package com.zapateria.main.dto;

/**
 *
 * @author Diego
 */
public class MaterialDTO implements java.io.Serializable {

    private static final long serialVersionUID = 7337130184410583413L;

    private long id;
    private String nombre;
    private String patron;
    
    

    public MaterialDTO(long id, String nombre,String patron) {
        this.id = id;
        this.nombre = nombre;
        this.patron=patron;
               
    }

    public MaterialDTO() {

    }

    public String getPatron() {
        return patron;
    }

    public void setPatron(String patron) {
        this.patron = patron;
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

   

    @Override
    public String toString() {
        return this.nombre;
    }

    public Object[] toArray() {
        return new Object[]{
            id, nombre,patron
        };
    }
}
