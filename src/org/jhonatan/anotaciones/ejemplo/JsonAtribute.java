package org.jhonatan.anotaciones.ejemplo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
//indicamos el target en donde se va aplicar esta anotacion
@Target(ElementType.FIELD) //sobre atributos
//sobre que contexto 
@Retention(RetentionPolicy.RUNTIME)//en tiempo de ejecucion
public @interface JsonAtribute {

    /*atributos*/
    String nombre() default "";

    boolean capitalizar() default false;

}
