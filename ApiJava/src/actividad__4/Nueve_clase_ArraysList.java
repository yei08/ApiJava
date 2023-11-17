/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.ArrayList;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Nueve_clase_ArraysList {

    public static void main(String[] args) {
        // Crear un objeto ArrayList
        ArrayList<String> nombres = new ArrayList<>();

        // Agregar elementos al ArrayList
        nombres.add("Juan");
        nombres.add("Maria");
        nombres.add("Pedro");
        nombres.add("Ana");

        // Imprimir el ArrayList
        System.out.println("ArrayList de nombres: " + nombres);

        // Obtener un elemento del ArrayList
        String nombre = nombres.get(2);
        System.out.println("El nombre en la posicion 2 es: " + nombre);

        // Eliminar un elemento del ArrayList
        nombres.remove(1);
        System.out.println("ArrayList de nombres despues de remover el indice 1: " + nombres);

        // Verificar si el ArrayList contiene un elemento específico
        boolean contienePedro = nombres.contains("Pedro");
        System.out.println("El ArrayList contiene a Pedro? " + contienePedro);

        // Obtener el tamaño del ArrayList
        int cantidadElementos = nombres.size();
        System.out.println("Cantidad de elementos en el ArrayList: " + cantidadElementos);
    }
}
