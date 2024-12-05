package Module;

public class Athlete {
    private int cod;  // Código del deportista
    private String name;  // Nombre del deportista
    private int codDeporte;  // Clave foránea del deporte que practica

    // Constructor
    public Athlete(int cod, String name, int codDeporte) {
        this.cod = cod;
        this.name = name;
        this.codDeporte = codDeporte;
    }

    // Getters y setters
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCodDeporte() {
        return codDeporte;
    }

    public void setCodDeporte(int codDeporte) {
        this.codDeporte = codDeporte;
    }
    
    @Override
    public String toString() {
        return "Nombre= " + name+
                "Codigo Deporte= " + codDeporte;
    }
      
}
