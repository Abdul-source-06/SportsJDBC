package DAO;

import Module.Athlete;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class athleteDAO implements DAO<Athlete> {
	
	 private Connection conn;

	    // Constructor que obtiene la conexion
	    public athleteDAO(Connection conn) {
	    	this.conn=conn; 
	    }

	    @Override
	    public void add(Athlete athlete) {
	        // Consulta para obtener el máximo valor de cod en la tabla deportistas
	        String sqlMaxCod = "SELECT MAX(cod) FROM deportistas";
	        String sqlInsert = "INSERT INTO deportistas (cod, nombre, cod_deporte) VALUES (?, ?, ?)";

	        try (Statement st = conn.createStatement();
	             PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
	            
	            // Ejecutar la consulta para obtener el máximo cod
	            ResultSet rs = st.executeQuery(sqlMaxCod);
	            
	            int newCod = 1; // Valor predeterminado para el primer deportista
	            if (rs.next()) {
	                newCod = rs.getInt(1) + 1; // Incrementa el valor máximo actual
	            }
	            
	            // Asignar valores a la consulta de inserción
	            ps.setInt(1, newCod); // Asignar el nuevo cod
	            ps.setString(2, athlete.getName()); // Asignar el nombre del deportista
	            ps.setInt(3, athlete.getCodDeporte()); // Asignar el código del deporte que practica
	            
	            // Ejecutar la consulta de inserción
	            ps.executeUpdate();

	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }


    @Override
    public List<Athlete> getAll() {
        List<Athlete> athletes = new ArrayList<>();
        String sql = "SELECT cod, nombre, cod_deporte FROM deportistas"; // Consulta para obtener todos los deportistas

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Athlete athlete = new Athlete(rs.getInt("cod"), rs.getString("nombre"), rs.getInt("cod_deporte")); // Creamos un objeto Athlete con los resultados
                athletes.add(athlete); // Agregamos el deportista a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return athletes; // Devolvemos la lista de deportistas
    }
    
 // Buscar deportistas por nombre (búsqueda parcial)
    public List<Athlete> searchByName(String name) {
        List<Athlete> athletes = new ArrayList<>();
        String sql = "SELECT cod, nombre, cod_deporte FROM deportistas WHERE nombre ILIKE ?"; // ILIKE para búsqueda insensible a mayúsculas/minúsculas

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%"); // Búsqueda parcial
            ResultSet rs = pstmt.executeQuery(); // Ejecutamos la consulta

            while (rs.next()) {
                Athlete athlete = new Athlete(rs.getInt("cod"), rs.getString("nombre"), rs.getInt("cod_deporte")); // Creamos un objeto Athlete
                athletes.add(athlete); // Agregamos el deportista a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return athletes; // Devolvemos la lista de deportistas encontrados
    }
    
    public void getBySport(int sportId) {
        String sql = "SELECT * FROM listar_deportistas_por_deporte(?)";  // Llamamos a la función PL/pgSQL

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sportId);  // Establecemos el ID del deporte
            ResultSet rs = ps.executeQuery();  // Ejecutamos la consulta

            while (rs.next()) {
                // Ahora obtenemos las columnas correctas
                int codDeportista = rs.getInt(1);  // Recuperamos el código del deportista
                String nombreDeportista = rs.getString(2);  // Recuperamos el nombre del deportista
                String deporteNombre = rs.getString(3);  // Recuperamos el nombre del deporte

                // Imprimimos los resultados
                System.out.println("Código Deportista: " + codDeportista + "\nNombre Deportista: " + nombreDeportista + "\nDeporte: " + deporteNombre + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
