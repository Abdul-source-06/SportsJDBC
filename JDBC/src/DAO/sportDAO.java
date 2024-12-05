package DAO;

import Module.Sport;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class sportDAO implements DAO<Sport> {

    private Connection conn;

    // Constructor que obtiene la conexion
    public sportDAO(Connection conn) {
    	this.conn=conn;
        
    }

    public void add(Sport sport) {
        try (Statement st = conn.createStatement()) {
            // Recupera el último valor de `cod` en la base de datos
            String sql = "SELECT MAX(cod) FROM deportes";
            ResultSet rs = st.executeQuery(sql);
            
            int newCod = 1; // Valor predeterminado para el primer deporte
            if (rs.next()) {
                newCod = rs.getInt(1) + 1;  // Incrementa el valor máximo actual
            }
            
            // Ahora insertamos el nuevo deporte con el nuevo `cod`
            String insertSql = "INSERT INTO deportes (cod, nombre) VALUES (" + newCod + ", '" + sport.getName() + "')";
            st.executeUpdate(insertSql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Sport> getAll() {
        List<Sport> sports = new ArrayList<>();
        String sql = "SELECT * FROM listar_deportes()";  // Consulta para obtener todos los deportes (sin parámetro)

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Sport sport = new Sport(rs.getInt(1), rs.getString(2)); // Creamos un objeto Sport con los resultados
                sports.add(sport); // Agregamos el deporte a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sports; // Devolvemos la lista de deportes
    }
}
