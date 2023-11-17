/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UtilizacionDeClases;

/**
 *
 * @author JEIFER ALCALA
 */
import java.util.*;

// Enumeración para representar las materias
enum Materia {
    MATEMATICA,
    LENGUAJE,
    CIENCIAS,
    HISTORIA
}

// Clase para representar a un estudiante con calificaciones en diferentes materias
class Estudiante {
    String nombre;
    Map<Materia, Integer> calificaciones;

    public Estudiante(String nombre) {
        this.nombre = nombre;
        this.calificaciones = new HashMap<>();
    }

    public void agregarCalificacion(Materia materia, int calificacion) {
        calificaciones.put(materia, calificacion);
    }

    public Map<Materia, Integer> getCalificaciones() {
        return calificaciones;
    }

    @Override
    public String toString() {
        return nombre + ": " + calificaciones;
    }
}

public class academia {
    public static void main(String[] args) {
        // Crear un conjunto de estudiantes
        Set<Estudiante> estudiantes = new HashSet<>();

        // Crear algunos estudiantes y asignar calificaciones
        Estudiante estudiante1 = new Estudiante("Juan");
        estudiante1.agregarCalificacion(Materia.MATEMATICA, 90);
        estudiante1.agregarCalificacion(Materia.LENGUAJE, 85);
        estudiante1.agregarCalificacion(Materia.CIENCIAS, 92);
        estudiante1.agregarCalificacion(Materia.HISTORIA, 88);
        estudiantes.add(estudiante1);
                                                                                    
        Estudiante estudiante2 = new Estudiante("Maria");
        estudiante2.agregarCalificacion(Materia.MATEMATICA, 78);
        estudiante2.agregarCalificacion(Materia.LENGUAJE, 92);
        estudiante2.agregarCalificacion(Materia.CIENCIAS, 85);
        estudiante2.agregarCalificacion(Materia.HISTORIA, 90);
        estudiantes.add(estudiante2);

        Estudiante estudiante3 = new Estudiante("Carlos");
        estudiante3.agregarCalificacion(Materia.MATEMATICA, 95);
        estudiante3.agregarCalificacion(Materia.LENGUAJE, 88);
        estudiante3.agregarCalificacion(Materia.CIENCIAS, 91);
        estudiante3.agregarCalificacion(Materia.HISTORIA, 82);
        estudiantes.add(estudiante3);

        // Mostrar la lista original de estudiantes
        System.out.println("Lista original de estudiantes:");
        for (Estudiante estudiante : estudiantes) {
            System.out.println(estudiante);
        }

        // Calcular y mostrar el promedio de calificaciones de cada estudiante
        System.out.println("\nPromedio de calificaciones:");
        for (Estudiante estudiante : estudiantes) {
            Map<Materia, Integer> calificaciones = estudiante.getCalificaciones();
            double promedio = calificaciones.values().stream()
                    .mapToInt(Integer::intValue)
                    .average()
                    .orElse(0.0);
            System.out.println(estudiante.nombre + ": " + promedio);
        }

        // Convertir el conjunto a una lista y ordenarla
        List<Estudiante> listaOrdenada = new ArrayList<>(estudiantes);
        Collections.sort(listaOrdenada, Comparator.comparing(estudiante -> estudiante.nombre));

        // Mostrar la lista ordenada y sin duplicados
        System.out.println("\nLista ordenada de estudiantes sin duplicados:");
        for (Estudiante estudiante : listaOrdenada) {
            System.out.println(estudiante);
        }
    }
}

