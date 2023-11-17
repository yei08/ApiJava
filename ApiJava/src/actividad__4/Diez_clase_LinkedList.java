/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.LinkedList;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Diez_clase_LinkedList {

    public static void main(String[] args) {
        // Crear un objeto LinkedList
        LinkedList<String> nombres = new LinkedList<>();

        // Agregar elementos a la lista enlazada
        nombres.add("Juan");
        nombres.add("Maria");
        nombres.add("Pedro");
        nombres.add("Ana");

        // Imprimir la lista enlazada
        System.out.println("Lista enlazada de nombres: " + nombres);

        // Obtener el primer elemento de la lista
        String primerNombre = nombres.getFirst();
        System.out.println("Primer nombre en la lista: " + primerNombre);

        // Obtener el último elemento de la lista
        String ultimoNombre = nombres.getLast();
        System.out.println("Ultimo nombre en la lista: " + ultimoNombre);

        // Eliminar el primer elemento de la lista
        nombres.removeFirst();
        System.out.println("Lista enlazada despues de eliminar el primer nombre: " + nombres);

        // Eliminar el último elemento de la lista
        nombres.removeLast();
        System.out.println("Lista enlazada despues de eliminar el ultimo nombre: " + nombres);

        // Verificar si la lista contiene un elemento específico
        boolean contienePedro = nombres.contains("Pedro");
        System.out.println("La lista contiene a Pedro? " + contienePedro);

        // Obtener el tamaño de la lista
        int tamaño = nombres.size();
        System.out.println("Tamano de la lista enlazada: " + tamaño);
    }
}
