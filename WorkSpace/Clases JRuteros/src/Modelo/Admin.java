package Modelo;

import java.time.LocalDate;

public class Admin extends Persona {

  private String nomAdmin;

  public Admin(String nombre, String apellido, String nomAdmin, String pass, String domicilio, Integer dni, LocalDate fecha, Sexo sexo, String mail) 
  {
	  
  	this.setNombre(nombre);
  	this.setApellido(apellido);
  	this.setNomAdmin(nomAdmin);
  	this.setPass(pass);
  	this.setDomicilio(domicilio);
  	this.setDni(dni);
  	this.setFechaNac(fecha);
  	this.setSexo(sexo);
  	this.setMail(mail);
  }

	public String getNomAdmin() {
		return nomAdmin;
	}
	
	public void setNomAdmin(String nomAdmin) {
		this.nomAdmin = nomAdmin;
	}

}