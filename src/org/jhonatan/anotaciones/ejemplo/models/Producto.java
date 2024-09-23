package org.jhonatan.anotaciones.ejemplo.models;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.jhonatan.anotaciones.ejemplo.Init;
import org.jhonatan.anotaciones.ejemplo.JsonAtribute;

public class Producto {

    //nombre y precio se van a expiortar o comvertir en este jSON
    //@JsonAtribute(capitalizar = true)
    @JsonAtribute
    private String nombre;
    @JsonAtribute(nombre = "precio")
    private long precio;
    private LocalDate fecha;

    @Init
    private void init() {
        //capitalizamos le nombre
        //convertimos  la primera letra de cada palabra en mayuscula
        this.nombre = nombre = Arrays.stream(nombre.split(" "))
                .map(palabra -> palabra.substring(0, 1).toUpperCase()
                + palabra.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

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
