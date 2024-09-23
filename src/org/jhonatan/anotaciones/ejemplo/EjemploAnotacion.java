package org.jhonatan.anotaciones.ejemplo;

import java.time.LocalDate;
import org.jhonatan.anotaciones.ejemplo.models.Producto;

public class EjemploAnotacion {

    public static void main(String[] args) {
        /*creamos una instancia de la clase producto*/
        Producto p = new Producto();

        //seteamos atributos
        p.setFecha(LocalDate.now());
        p.setNombre("mesa centro roble");
        p.setPrecio(1000L);

    }

}
