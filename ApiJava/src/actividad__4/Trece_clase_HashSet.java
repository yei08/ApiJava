/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.HashSet;
import java.util.Set;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Trece_clase_HashSet {

    public static void main(String[] args) {
        // Crear un objeto HashSet
        Set<String> nombres = new HashSet<>();

        // Agregar elementos al HashSet
        nombres.add("Juan");
        nombres.add("Maria");
        nombres.add("Pedro");
        nombres.add("Ana");
        nombres.add("Maria"); // Intentamos agregar un elemento duplicado

        // Imprimir el HashSet completo
        System.out.println("HashSet de nombres: " + nombres);

        // Verificar si el HashSet contiene un elemento específico
        boolean contienePedro = nombres.contains("Pedro");
        System.out.println("El HashSet contiene a Pedro? " + contienePedro);

        // Eliminar un elemento del HashSet
        nombres.remove("Ana");
        System.out.println("HashSet de nombres despues de eliminar a Ana: " + nombres);

        // Obtener el tamaño del HashSet
        int tamaño = nombres.size();
        System.out.println("Tamano del HashSet de nombres: " + tamaño);
    }
}
