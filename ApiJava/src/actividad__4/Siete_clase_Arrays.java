/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.Arrays;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Siete_clase_Arrays {

    public static void main(String[] args) {
        // Crear un array de números
        int[] numeros = {5, 2, 8, 3, 1};

        // Ordenar el array de forma ascendente
        Arrays.sort(numeros);

        // Imprimir el array ordenado
        System.out.println("Array ordenado: " + Arrays.toString(numeros));

        // Buscar un elemento en el array
        int indice = Arrays.binarySearch(numeros, 3);
        System.out.println("Indice del elemento 3: " + indice);

        // Copiar un array a otro
        int[] copia = Arrays.copyOf(numeros, numeros.length);

        // Comprobar si dos arrays son iguales
        boolean sonIguales = Arrays.equals(numeros, copia);
        System.out.println("Los arrays son iguales? " + sonIguales);
    }
}
