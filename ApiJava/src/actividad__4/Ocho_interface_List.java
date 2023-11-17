/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.ArrayList;
import java.util.List;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Ocho_interface_List {

    public static void main(String[] args) {
        List<String> nombres = new ArrayList<>();

        nombres.add("Juan");
        nombres.add("Maria");
        nombres.add("Pedro");
        nombres.add("Ana");

        System.out.println("Lista de nombres: " + nombres);

        String nombre = nombres.get(2);
        System.out.println("El nombre en la posicion 2 es: " + nombre);

        nombres.remove(1);
        System.out.println("Lista de nombres despues de remover el indice 1: " + nombres);

        boolean contienePedro = nombres.contains("Pedro");
        System.out.println("La lista contiene a Pedro? " + contienePedro);

        int cantidadElementos = nombres.size();
        System.out.println("Cantidad de elementos en la lista: " + cantidadElementos);

        nombres.add(0, "Luis");
        System.out.println("Lista de nombres despues de agregar Luis en la posicion 0 (Cero) : " + nombres);

        nombres.sort(String::compareTo);
        System.out.println("Lista de nombres ordenada alfabeticamente: " + nombres);

        nombres.clear();
        System.out.println("Lista de nombres despues de limpiarla: " + nombres);
    }
}
