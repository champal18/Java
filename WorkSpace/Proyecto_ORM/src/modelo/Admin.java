package modelo;

public class Admin extends Persona {

	long id;
  private String nomAdmin;

  public Admin(){}

	public String getNomAdmin() {
		return nomAdmin;
	}
	
	public void setNomAdmin(String nomAdmin) {
		this.nomAdmin = nomAdmin;
	}

}