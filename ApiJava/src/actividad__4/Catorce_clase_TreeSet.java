/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.Set;
import java.util.TreeSet;

/*
 * @author JEIFER ALCALA
 *
 */
public class Catorce_clase_TreeSet {

    public static void main(String[] args) {
        // Crear un objeto TreeSet
        Set<String> nombres = new TreeSet<>();

        // Agregar elementos al TreeSet
        nombres.add("Juan");
        nombres.add("juana");
        nombres.add("Pedro");
        nombres.add("Ana");
        nombres.add("Maria"); // Intentamos agregar un elemento duplicado

        // Imprimir el TreeSet completo
        System.out.println("TreeSet de nombres: " + nombres);

        // Verificar si el TreeSet contiene un elemento específico
        boolean contienePedro = nombres.contains("Pedro");
        System.out.println("El TreeSet contiene a Pedro? " + contienePedro);

        // Eliminar un elemento del TreeSet
        nombres.remove("Ana");
        System.out.println("TreeSet de nombres despues de eliminar a Ana: " + nombres);

        // Obtener el tamaño del TreeSet
        int tamaño = nombres.size();
        System.out.println("Tamano del TreeSet de nombres: " + tamaño);
    }
}
