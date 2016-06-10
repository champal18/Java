package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Usuario extends Persona {

	long id;
  private String nomUsuario;
  private ArrayList<Ruta> misRutas;
  private ArrayList<RutaRealizada> rutasRealizadas;

  /*
   * Puede tener 0-N rutas
   * Puede tener 0-N rutasRealizadas
   */
  
  public Usuario(){}

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