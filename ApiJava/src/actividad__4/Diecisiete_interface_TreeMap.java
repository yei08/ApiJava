/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.Map;
import java.util.TreeMap;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Diecisiete_interface_TreeMap {

    public static void main(String[] args) {
        // Crear un objeto TreeMap
        Map<String, Integer> edades = new TreeMap<>();

        // Agregar pares clave-valor al TreeMap
        edades.put("Juan David", 25);
        edades.put("Enrique", 30);
        edades.put("Luis Diaz", 28);
        edades.put("Ana", 35);

        // Obtener la edad de "Juan David" utilizando el método get()
        int edadJuanDavid = edades.get("Juan David");
        System.out.println("La edad de Juan David es: " + edadJuanDavid + " anios.");

        // Verificar si el TreeMap contiene una clave específica utilizando containsKey()
        String clave = "Enrique";
        if (edades.containsKey(clave)) {
            System.out.println("El TreeMap contiene la clave: " + clave);
        } else {
            System.out.println("El TreeMap no contiene la clave: " + clave);
        }

        // Eliminar un elemento del TreeMap utilizando remove()
        edades.remove("Luis Diaz");

        // Imprimir las claves, valores y pares clave-valor del TreeMap
        System.out.println("Claves en el TreeMap: " + edades.keySet());
        System.out.println("Valores en el TreeMap: " + edades.values());
        System.out.println("Pares clave-valor en el TreeMap: " + edades.entrySet());
    }
}
