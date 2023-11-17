/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.Properties;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Tres_clase_Properties_Property {

    public static void main(String[] args) {
        // Crear un objeto Properties
        Properties properties = new Properties();

        // Agregar propiedades al objeto Properties
        properties.setProperty("nombre", "Rene  Descartes");
        properties.setProperty("edad", "33");
        properties.setProperty("ciudad", "Cartagena");

        // Obtener el valor de una propiedad
        String nombre = properties.getProperty("nombre");
        System.out.println("Nombre: " + nombre);

        // Obtener todas las propiedades
        System.out.println("Propiedades:");
        properties.forEach((key, value) -> {
            System.out.println(key + ": " + value);
        });

        // Verificar si una propiedad existe
        boolean existeEdad = properties.containsKey("edad");
        System.out.println("Existe la propiedad 'edad': " + existeEdad);

        // Eliminar una propiedad
        properties.remove("ciudad");

        // Verificar si el objeto Properties está vacío
        boolean estaVacio = properties.isEmpty();
        System.out.println("El objeto Properties esta vacio?: " + estaVacio);
    }
}
