package Modelo;
public class Actividad {

  private String nombre;

  private Boolean habilitada;

  public Actividad(String nombre, Boolean habilitacion)
  {
	  this.nombre = nombre;
	  this.habilitada = habilitacion;
  }
    
  public String getNombre()
  {
    return this.nombre;
  }

  public void setNombre(String nombre) 
  {
    this.nombre = nombre;
  }

  public Boolean getHabilitada() 
  {
    return this.habilitada;
  }

  public void setHabilitada(Boolean habilitada) 
  {
    this.habilitada = habilitada;
  }

}