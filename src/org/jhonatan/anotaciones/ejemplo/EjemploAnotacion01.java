package org.jhonatan.anotaciones.ejemplo;

import java.time.LocalDate;
import org.jhonatan.anotaciones.ejemplo.models.Producto;
import org.jhonatan.anotaciones.ejemplo.procesador.JsonSerializador;

public class EjemploAnotacion01 {

    public static void main(String[] args) {
        /*creamos una instancia de la clase producto*/
        Producto pro = new Producto();

        //seteamos atributos
        pro.setFecha(LocalDate.now());
        pro.setNombre("laptop Lenovo G40");
        pro.setPrecio(3450L);

        System.out.println("Json: " + JsonSerializador.convertirJson(pro));
    }

}
