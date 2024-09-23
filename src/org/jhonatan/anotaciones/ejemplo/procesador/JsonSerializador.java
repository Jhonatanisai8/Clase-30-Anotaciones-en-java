package org.jhonatan.anotaciones.ejemplo.procesador;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jhonatan.anotaciones.ejemplo.Init;
import org.jhonatan.anotaciones.ejemplo.JsonAtribute;
import org.jhonatan.anotaciones.ejemplo.procesador.exception.JsonSerializadorException;

public class JsonSerializador {

    //metodo que se encarga de inicializar el objeto antes de convertir a json
    public static void inicializarObject(Object object) {
        //si el objeto es nulo lanzamos una excepcion
        if (object == null) {
            throw new JsonSerializadorException("El objeto a serilizar no puede ser null!: ");
        }
        //obtenemos los atributos
        Method[] metodos = object.getClass().getDeclaredMethods();
        Arrays.stream(metodos).filter(m -> m.isAnnotationPresent(Init.class))
                .forEach(m -> {
                    m.setAccessible(true);
                    try {
                        m.invoke(object);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        throw new JsonSerializadorException("Error al serializar, no se puede iniciar el objeto: " + ex.getMessage());
                    }
                });
    }

    public static String convertirJson(Object object) {

        //si el objeto es nulo lanzamos una excepcion
        if (object == null) {
            throw new JsonSerializadorException("El objeto a serilizar no puede ser null!: ");
        }
        //Lllamados al metodo
        inicializarObject(object);
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
                            
                            nuevoValor = String.valueOf(nuevoValor.charAt(0))
                                    + nuevoValor.substring(1).toLowerCase();
                             */

                            //convertimos  la primera letra de cada palabra en mayuscula
                            nuevoValor = Arrays.stream(nuevoValor.split(" "))
                                    .map(palabra -> palabra.substring(0, 1).toUpperCase()
                                    + palabra.substring(1).toLowerCase())
                                    .collect(Collectors.joining(" "));
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
