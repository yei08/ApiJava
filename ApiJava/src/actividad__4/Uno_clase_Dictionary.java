/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Uno_clase_Dictionary {

    public static void main(String[] args) {
        // Crear un objeto Dictionary
        Dictionary<String, Integer> diccionario = new Hashtable<>();

        // Agregar elementos al diccionario
        diccionario.put("Pera", 10);
        diccionario.put("Naranja", 5);
        diccionario.put("Banana", 8);

        // Obtener el valor asociado a una clave
        int cantidadPeras = diccionario.get("Pera");
        System.out.println("Cantidad de Peras: " + cantidadPeras);

        // Obtener todas las claves del diccionario
        Enumeration<String> keys = diccionario.keys();
        System.out.println("Claves del diccionario:");
        while (keys.hasMoreElements()) {
            String clave = keys.nextElement();
            System.out.println(clave);
        }

        // Obtener todas los valores del diccionario
        Enumeration<Integer> values = diccionario.elements();
        System.out.println("Valores del diccionario:");
        while (values.hasMoreElements()) {
            int valor = values.nextElement();
            System.out.println(valor);
        }

        // Eliminar un elemento del diccionario
        diccionario.remove("Banana");

        // Verificar si el diccionario está vacío
        boolean estaVacio = diccionario.isEmpty();
        System.out.println("El diccionario esta vacio: " + estaVacio);

        // Obtener el tamaño del diccionario
        int tamano = diccionario.size();
        System.out.println("Tamano del diccionario: " + tamano);
    }
}
