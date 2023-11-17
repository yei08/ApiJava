/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package actividad__4;

import java.util.HashSet;
import java.util.Set;

/*
 * @author JEIFER ALCALA
 * 
 */
public class Once_interface_Set {

    public static void main(String[] args) {

        Set<Integer> numeros = new HashSet<>();

        numeros.add(5);
        numeros.add(10);
        numeros.add(15);
        numeros.add(20);
        numeros.add(25);
        numeros.add(10);

        System.out.println("Este es el conjunto de numeros: " + numeros);

        int elemento = 15;
        if (numeros.contains(elemento)) {
            System.out.println("Este conjunto contiene el elemento " + elemento);
        } else {
            System.out.println("Este conjunto no contiene el elemento " + elemento);
        }

        numeros.remove(20);

        System.out.println("Este es el conjunto de numeros actualizado: " + numeros);

        System.out.println("Esta es la cantidad de elementos en el conjunto: " + numeros.size());

        if (numeros.isEmpty()) {
            System.out.println("Este conjunto esta vacio");
        } else {
            System.out.println("Este conjunto no esta vacio");
        }

        numeros.clear();

        System.out.println("Asi queda el conjunto despues de limpiarlo: " + numeros);
    }
}
