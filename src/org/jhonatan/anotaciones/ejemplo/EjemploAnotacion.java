package org.jhonatan.anotaciones.ejemplo;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import org.jhonatan.anotaciones.ejemplo.models.Producto;

public class EjemploAnotacion {

    public static void main(String[] args) {
        /*creamos una instancia de la clase producto*/
        Producto pro = new Producto();

        //seteamos atributos
        pro.setFecha(LocalDate.now());
        pro.setNombre("mesa centro roble");
        pro.setPrecio(1000L);

        //procemos 
        //obtenemos los atributos
        Field[] atributos = pro.getClass().getDeclaredFields();
        //stream de campos
        String jSon = Arrays.stream(atributos)
                .filter(f -> f.isAnnotationPresent(JsonAtribute.class))
                .map(f -> {
                    //dejamos accisebles los atributos
                    f.setAccessible(true);
                    String nombre = f.getAnnotation(JsonAtribute.class).nombre().equals("")
                            ? f.getName()
                            : f.getAnnotation(JsonAtribute.class).nombre();
                    try {
                        return "\"" + nombre + "\":\"" + f.get(pro) + "\"";
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException("error al serializar el jSon: " + ex.getMessage());
                    }
                })
                .reduce("{", (a, b) -> {
                    if ("{".equals(a)) {
                        return a + b;
                    }
                    return a + ", " + b;
                }).concat("}");
        System.out.println("Json: " + jSon);
    }

}
