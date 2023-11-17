/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.Vector;

/*
 * @author LUIS DAVID PALACIO DIAZ
 * Codigo  7502210079
 */
public class Cuatro_clase_Vector {

    public static void main(String[] args) {

        // Crear un objeto Vector
        Vector<String> vector = new Vector<>();

        // Agregar elementos al vector
        vector.add("Pera");
        vector.add("Naranja");
        vector.add("Banana");

        // Obtener el tamaño del vector
        int tamaño = vector.size();
        System.out.println("Tamano del vector: " + tamaño);

        // Obtener un elemento por índice
        String elemento = vector.get(1);
        System.out.println("Elemento en la posicion 1: " + elemento);

        // Verificar si el vector contiene un elemento
        boolean contieneBanana = vector.contains("Banana");
        System.out.println("El vector contiene 'Banana' : " + contieneBanana);

        // Eliminar un elemento por índice
        vector.remove(0);

        // Verificar si el vector está vacío
        boolean estaVacio = vector.isEmpty();
        System.out.println("El vector esta vacio?: " + estaVacio);

        // Recorrer el vector
        System.out.println("Elementos del vector:");
        for (String item : vector) {
            System.out.println(item);
        }
    }
}
