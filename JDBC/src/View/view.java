package View;

import Module.Sport;
import Module.Athlete;
import java.util.List;
import java.util.Scanner;

public class view {
   static Scanner sc = new Scanner(System.in);

    public Sport SportForm() {          
          System.out.println("Por favor ingrese el nombre del deporte:");
          while(sc.hasNextInt()) {
        	  System.err.println("Input invalido, Por favor ingrese el nombre del deporte:");
        	  sc.next();
          }
          String name = sc.nextLine();
          
          Sport sport = new Sport(0, name);  // 0 porque el 'cod' (ID) será autoincrementado en la base de datos

          return sport;
    }

    
    public Athlete AthleteForm(List<Sport> sports) {
    

          // Solicitar el nombre del deportista
          System.out.println("Por favor ingrese el nombre del deportista:");
          while(sc.hasNextInt()) {
        	  System.err.println("Entrada invalida, Por favor ingrese el nombre del deportista:");
        	  sc.next();
          }
          String name = sc.nextLine();

          // Mostrar lista de deportes disponibles
          System.out.println("Seleccione el deporte que practica el deportista:");
          for (int i = 0; i < sports.size(); i++) {
              System.out.println((i + 1) + ". " + sports.get(i).getName());
          }

       // Solicitar al usuario que ingrese el número correspondiente al deporte
          int sportChoice = -1;
          while (sportChoice < 1 || sportChoice > sports.size()) {
              System.out.println("Ingrese el número del deporte:");
              while (!sc.hasNextInt()) {
                  System.err.println("Entrada inválida, por favor ingrese un número:");
                  sc.next();
              }
              sportChoice = sc.nextInt();
              sc.nextLine();

              if (sportChoice < 1 || sportChoice > sports.size()) {
                  System.err.println("Opción no válida. Inténtelo de nuevo.");
              }
          }
          // Obtener el código del deporte seleccionado
          int codDeporte = sports.get(sportChoice - 1).getCod();

          // Crear y retornar el objeto Athlete
          Athlete athlete = new Athlete(0, name, codDeporte);  // 0 es el código, ya que será autoincrementado por la base de datos
          return athlete;
    }
    
    public int AskSport() {
        int sportId = -1;  // Variable para almacenar el ID del deporte

        // Continuamos pidiendo la entrada hasta que sea válida
        while (sportId < 0) {
            System.out.print("Introduce el ID del deporte: " + "\n");

            // Intentamos leer la entrada del usuario
            try {
                sportId = sc.nextInt();

                // Verificamos que el ID sea válido (por ejemplo, no negativo)
                if (sportId < 0) {
                    System.out.println("Entrada inválida. El ID del deporte no puede ser negativo. Por favor, inténtalo de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, introduce un ID de deporte válido.");
                sc.nextLine(); 
            }
        }

        return sportId;  // Devolvemos el ID del deporte validado
    }



    public void AthleteList(List<Athlete> athletes) {
        System.out.println("Lista de deportistas:");
        for (Athlete athlete : athletes) {
            System.out.println("Nombre: " + athlete.getName() + ", Código Deporte: " + athlete.getCodDeporte());
        }
    }
    
    

    public void SportsList(List<Sport> sports) {
    	 System.out.println("Lista de deportes: \n");
         for (Sport sport :sports) {
             System.out.println("Nombre: " + sport.getName() + " - Código Deporte: " + sport.getCod() + "\n");
         }
    }  
}

