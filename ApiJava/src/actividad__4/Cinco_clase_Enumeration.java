/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package actividad__4;

import java.util.Enumeration;
import java.util.Vector;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Cinco_clase_Enumeration {

    public static void main(String[] args) {
        // Crear un objeto Vector
        Vector<String> frutas = new Vector<>();

        // Agregar elementos al vector
        frutas.add("Pera");
        frutas.add("Sandia");
        frutas.add("Banana");

        // Obtener un objeto Enumeration de las frutas
        Enumeration<String> enumeracion = frutas.elements();

        // Iterar sobre los elementos utilizando Enumeration
        System.out.println("Elementos de las frutas:");
        while (enumeracion.hasMoreElements()) {
            String fruta = enumeracion.nextElement();
            System.out.println(fruta);
        }
    }
}
