/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.Hashtable;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Dos_clase_Hashtable {

    public static void main(String[] args) {
        // Crear un objeto Hashtable
        Hashtable<String, Integer> hashtable = new Hashtable<>();

        // Agregar elementos al hashtable
        hashtable.put("Pera", 10);
        hashtable.put("Naranja", 5);
        hashtable.put("Banana", 8);

        // Obtener el valor asociado a una clave
        int cantidadPeras = hashtable.get("Pera");
        System.out.println("Cantidad de Peras: " + cantidadPeras);

        // Verificar si una clave existe en el hashtable
        boolean existeBanana = hashtable.containsKey("Banana");
        System.out.println("Existe la clave 'Banana': " + existeBanana);

        // Verificar si un valor existe en el hashtable
        boolean existeValor5 = hashtable.containsValue(5);
        System.out.println("Existe el valor 5: " + existeValor5);

        // Obtener el tamaño del hashtable
        int tamaño = hashtable.size();
        System.out.println("Tamano del hashtable: " + tamaño);

        // Eliminar un elemento del hashtable
        hashtable.remove("Naranja");

        // Verificar si el hashtable está vacío
        boolean estaVacio = hashtable.isEmpty();
        System.out.println("El hashtable esta vacio?: " + estaVacio);
    }
}
