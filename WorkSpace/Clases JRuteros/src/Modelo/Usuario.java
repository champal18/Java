package Modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario extends Persona {

  private String nomUsuario;
  private ArrayList<Ruta> misRutas;
  private ArrayList<RutaRealizada> rutasRealizadas;

  public Usuario(String nombre, String apellido, String nomUsuario, String pass, String domicilio, Integer dni, LocalDate fecha, Sexo sexo, String mail) 
  {
	this.setNombre(nombre);
  	this.setApellido(apellido);
  	this.setNomUsuario(nomUsuario);
  	this.setPass(pass);
  	this.setDomicilio(domicilio);
  	this.setDni(dni);
  	this.setFechaNac(fecha);
  	this.setSexo(sexo);
  	this.setMail(mail);
  }

public String getNomUsuario() {
	return nomUsuario;
}

public void setNomUsuario(String nomUsuario) {
	this.nomUsuario = nomUsuario;
}

public ArrayList<Ruta> getMisRutas() {
	return misRutas;
}

public void setMisRutas(ArrayList<Ruta> misRutas) {
	this.misRutas = misRutas;
}

public ArrayList<RutaRealizada> getRutasRealizadas() {
	return rutasRealizadas;
}

public void setRutasRealizadas(ArrayList<RutaRealizada> rutasRealizadas) {
	this.rutasRealizadas = rutasRealizadas;
}


}