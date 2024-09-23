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

                        //obtenemos el valor 
                        Object valor = f.get(object);
                        if (f.getAnnotation(JsonAtribute.class).capitalizar()
                                && valor instanceof String) {
                            String nuevoValor = (String) valor;
                            //tomamos el primer caracter y lo convetimos a mayusculas
                            /*
                            //otra forma
                            nuevoValor = nuevoValor.substring(0, 1).toUpperCase()
                                    + nuevoValor.substring(1).toLowerCase();
                             */
                            nuevoValor = String.valueOf(nuevoValor.charAt(0))
                                    + nuevoValor.substring(1).toLowerCase();
                            //seteamos el nuevo valor
                            f.set(object, nuevoValor);
                        }
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
