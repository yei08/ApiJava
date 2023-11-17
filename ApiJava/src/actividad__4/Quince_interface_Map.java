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
public class Quince_interface_Map {

    public static void main(String[] args) {
        Map<String, Integer> edades = new HashMap<>();

        edades.put("Juan David", 25);
        edades.put("Saulo David", 30);
        edades.put("Luis Palacio", 28);
        edades.put("Ana", 35);

        String nombre = "Juan David";
        int edad = edades.get(nombre);
        System.out.println(nombre + " tiene " + edad + " anos.");

        String clave = "Saulo David";
        if (edades.containsKey(clave)) {
            System.out.println("El mapa contiene la clave: " + clave);
        } else {
            System.out.println("El mapa no contiene la clave: " + clave);
        }

        edades.remove("Luis Palacio");

        System.out.println("Claves en el mapa: " + edades.keySet());
        System.out.println("Valores en el mapa: " + edades.values());
        System.out.println("Pares clave-valor en el mapa: " + edades.entrySet());
    }
}
