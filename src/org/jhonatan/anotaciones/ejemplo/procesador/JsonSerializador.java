package org.jhonatan.anotaciones.ejemplo.procesador;

import java.lang.reflect.Field;
import java.util.Arrays;
import org.jhonatan.anotaciones.ejemplo.JsonAtribute;
import org.jhonatan.anotaciones.ejemplo.procesador.exception.JsonSerializadorException;

public class JsonSerializador {

    public static String convertirJson(Object object) {

        //si el objeto es nulo lanzamos una excepcion
        if (object == null) {
            throw new JsonSerializadorException("El objeto a serilizar no puede ser null!: ");
        }
        //obtenemos los atributos
        Field[] atributos = object.getClass().getDeclaredFields();
        return Arrays.stream(atributos)
                .filter(f -> f.isAnnotationPresent(JsonAtribute.class))
                .map(f -> {
                    //dejamos accisebles los atributos
                    f.setAccessible(true);
                    String nombre = f.getAnnotation(JsonAtribute.class).nombre().equals("")
                            ? f.getName()
                            : f.getAnnotation(JsonAtribute.class).nombre();
                    try {
                        return "\"" + nombre + "\":\"" + f.get(object) + "\"";
                    } catch (IllegalAccessException ex) {
                        throw new JsonSerializadorException("error al serializar el jSon: " + ex.getMessage());
                    }
                })
                .reduce("{", (a, b) -> {
                    if ("{".equals(a)) {
                        return a + b;
                    }
                    return a + ", " + b;
                }).concat("}");
    }
}
