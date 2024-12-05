package Module;

public class Sport {
    private int cod;
    private String name;

  public Sport(int cod, String name) {
	this.cod=cod;
	this.name=name;
  }

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
  

@Override
public String toString() {
    return name;
}
  
}
