package XML;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ReadXML {
    private String host;
    private String port;
    private String user;
    private String password;
    private String database;

    public ReadXML(String configFile) {
        // Cargar el archivo XML
        File file = new File(configFile);

        try {
            // Crear el analizador XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            // Normalizar el documento
            doc.getDocumentElement().normalize();

            // Leer cada valor dentro de <cb-config>
            NodeList hostNode = doc.getElementsByTagName("host");
            NodeList portNode = doc.getElementsByTagName("port");
            NodeList userNode = doc.getElementsByTagName("user");
            NodeList passwordNode = doc.getElementsByTagName("password");
            NodeList databaseNode = doc.getElementsByTagName("database");

            // Asignar los valores leídos
            if (hostNode.getLength() > 0) {
                this.host = hostNode.item(0).getTextContent();
            }
            if (portNode.getLength() > 0) {
                this.port = portNode.item(0).getTextContent();
            }
            if (userNode.getLength() > 0) {
                this.user = userNode.item(0).getTextContent();
            }
            if (passwordNode.getLength() > 0) {
                this.password = passwordNode.item(0).getTextContent();
            }
            if (databaseNode.getLength() > 0) {
                this.database = databaseNode.item(0).getTextContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para obtener la conexión a la base de datos
    public Connection dbConnect() {
        Connection connection = null;
        try {
            // Crear la URL de conexión
            String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    

    // Métodos getter para acceder a los valores leídos desde el archivo XML
    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }
}
