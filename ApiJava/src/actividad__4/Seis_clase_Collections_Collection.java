/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Seis_clase_Collections_Collection {

    public static void main(String[] args) {
        // Crear una colección de números
        Collection<Integer> numeros = new ArrayList<>();

        // Agregar elementos a la colección
        numeros.add(5);
        numeros.add(2);
        numeros.add(8);

        // Imprimir la colección
        System.out.println("Coleccion original: " + numeros);

        // Ordenar la colección de forma ascendente
        Collections.sort((ArrayList<Integer>) numeros);

        // Imprimir la colección ordenada
        System.out.println("Coleccion ordenada: " + numeros);

        // Verificar si la colección contiene un elemento específico
        boolean contieneCinco = numeros.contains(5);
        System.out.println("La coleccion contiene el numero 5?: " + contieneCinco);

        // Eliminar un elemento de la colección
        numeros.remove(2);

        // Verificar si la colección está vacía
        boolean estaVacia = numeros.isEmpty();
        System.out.println("La coleccion esta vacia?: " + estaVacia);

        // Obtener el tamaño de la colección
        int tamaño = numeros.size();
        System.out.println("Tamano de la coleccion: " + tamaño);
    }
}
