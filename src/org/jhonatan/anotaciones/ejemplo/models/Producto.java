package org.jhonatan.anotaciones.ejemplo.models;

import java.time.LocalDate;
import org.jhonatan.anotaciones.ejemplo.JsonAtribute;

public class Producto {

    //nombre y precio se van a expiortar o comvertir en este jSON
    @JsonAtribute(nombre = "descripción")
    private String nombre;
    @JsonAtribute(nombre = "precio")
    private long precio;
    private LocalDate fecha;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio = precio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}
