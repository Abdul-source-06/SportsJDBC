package Controller;

import View.view;
import XML.ReadXML;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import DAO.sportDAO;
import DAO.athleteDAO;
import Module.Sport;
import Module.Athlete;

public class SportsManager {

    public static void main(String[] args) {
        // Cargar la configuración desde el archivo XML
        String configFile = "src/xml/database.xml";  // Ruta a tu archivo XML
        ReadXML config = new ReadXML(configFile);    // Cargar configuración desde el XML
        
        // Obtener la conexión a la base de datos usando la configuración
        Connection connection = config.dbConnect();
        if (connection == null) {
            System.out.println("No se pudo conectar a la base de datos.");
            return;
        }

        // Crear la instancia de sportDAO y athleteDAO pasándole la conexión
        sportDAO sportDAO = new sportDAO(connection);
        athleteDAO athleteDAO = new athleteDAO(connection);
        
        view View = new view();
        
        // Menú interactivo con Switch Case
        Scanner sc = new Scanner(System.in);
        int option = 0;

        while (true) {
            // Mostrar el menú de opciones
            System.out.println("Seleccione una opción:\n");
            System.out.println("1. Agregar un deporte\n");
            System.out.println("2. Listar deportes\n");
            System.out.println("3. Agregar un deportista\n");
            System.out.println("4. Listar deportistas por deporte\n");
            System.out.println("5. Salir");
            
            // Leer la opción seleccionada
            if (sc.hasNextInt()) {
                option = sc.nextInt();
                sc.nextLine(); 
            } else {
                System.err.println("Entrada no válida. Intente de nuevo.");
                sc.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    // Opción para agregar un deporte
                    Sport newSport = View.SportForm();  // Mostrar el formulario para agregar deporte
                    sportDAO.add(newSport);  // Llamar al DAO para agregar el deporte a la base de datos
                    System.out.println("Deporte agregado con éxito.");
                    break;

                case 2:
                    // Opción para listar todos los deportes
                    List<Sport> sports = sportDAO.getAll();
                    View.SportsList(sports);  // Mostrar los deportes en la vista
                    break;

                case 3:
                    // Opción para agregar un deportista
                    List<Sport> availableSports = sportDAO.getAll();  // Obtener todos los deportes
                    Athlete newAthlete = View.AthleteForm(availableSports);  // Mostrar formulario de deportista
                    if (newAthlete != null) {
                        athleteDAO.add(newAthlete);  // Agregar deportista a la base de datos
                        System.out.println("Deportista agregado con éxito.");
                    }
                    break;

                case 4:
                	View.SportsList(sportDAO.getAll());
                	
                	athleteDAO.getBySport(View.AskSport());

                    break;

                case 5:
                    // Opción para salir
                    System.out.println("Saliendo...");
                    sc.close();
                    return;  // Terminar el programa

                default:
                    System.err.println("Opción no válida, por favor intente de nuevo.");
                    break;
            }
        }
    }
}
