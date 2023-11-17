/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.HashMap;
import java.util.Map;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Dieciseis_interface_HashMap {

    public static void main(String[] args) {
        // Crear un objeto HashMap
        Map<String, Integer> edades = new HashMap<>();

        // Agregar pares clave-valor al HashMap
        edades.put("Juan David", 25);
        edades.put("Enrique", 30);
        edades.put("Luis Diaz", 28);
        edades.put("Ana", 35);

        // Obtener la edad de "Juan David" utilizando el método get()
        int edadJuanDavid = edades.get("Juan David");
        System.out.println("La edad de Juan David es: " + edadJuanDavid + " anos.");

        // Verificar si el HashMap contiene una clave específica utilizando containsKey()
        String clave = "Enrique";
        if (edades.containsKey(clave)) {
            System.out.println("El HashMap contiene la clave: " + clave);
        } else {
            System.out.println("El HashMap no contiene la clave: " + clave);
        }

        // Eliminar un elemento del HashMap utilizando remove()
        edades.remove("Luis Diaz");

        // Imprimir las claves, valores y pares clave-valor del HashMap
        System.out.println("Claves en el HashMap: " + edades.keySet());
        System.out.println("Valores en el HashMap: " + edades.values());
        System.out.println("Pares clave-valor en el HashMap: " + edades.entrySet());
    }
}
