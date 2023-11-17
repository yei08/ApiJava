/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.SortedSet;
import java.util.TreeSet;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Doce_clase_SortedSet {

    public static void main(String[] args) {
        // Crear un objeto SortedSet
        SortedSet<Integer> numeros = new TreeSet<>();

        // Agregar elementos al SortedSet
        numeros.add(5);
        numeros.add(10);
        numeros.add(15);
        numeros.add(20);
        numeros.add(25);

        // Imprimir el SortedSet completo
        System.out.println("SortedSet de numeros: " + numeros);

        // Obtener el primer elemento del SortedSet
        int primerNumero = numeros.first();
        System.out.println("Primer numero en el SortedSet: " + primerNumero);

        // Obtener el último elemento del SortedSet
        int ultimoNumero = numeros.last();
        System.out.println("Ultimo numero en el SortedSet: " + ultimoNumero);

        // Obtener un subconjunto del SortedSet
        SortedSet<Integer> subconjunto = numeros.subSet(10, 20);
        System.out.println("Subconjunto del SortedSet: " + subconjunto);

        // Verificar si el SortedSet contiene un elemento específico
        boolean contiene15 = numeros.contains(15);
        System.out.println("El SortedSet contiene el numero 15? " + contiene15);

        // Eliminar un elemento del SortedSet
        numeros.remove(20);
        System.out.println("SortedSet de numeros despues de eliminar el numero 20: " + numeros);

        // Obtener el tamaño del SortedSet
        int tamaño = numeros.size();
        System.out.println("Tamano del SortedSet de numeros: " + tamaño);
    }
}
